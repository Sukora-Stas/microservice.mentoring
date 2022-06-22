package com.epam.microservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Autowired
    public AwsS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public byte[] getFileBytesByKey(String key) {
        try {
            return amazonS3.getObject(bucketName, key).getObjectContent().readAllBytes();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public S3Object downloadResource(String fileName) {
        return amazonS3.getObject(bucketName, fileName);
    }

    public String saveFile(MultipartFile file) {
        try {
            var metaData = new ObjectMetadata();
            String filename = file.getOriginalFilename();
            metaData.setContentLength(file.getSize());
            amazonS3.putObject(bucketName,
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    metaData);

            return filename;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteByFileKeys(List<String> keys) {
        DeleteObjectsRequest deleteObjectsRequest =
                new DeleteObjectsRequest(bucketName).withKeys(keys.toArray(String[]::new));
        amazonS3.deleteObjects(deleteObjectsRequest);
    }
}
