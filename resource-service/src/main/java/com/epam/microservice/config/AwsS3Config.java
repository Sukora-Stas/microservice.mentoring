package com.epam.microservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Value("${cloud.aws.region}")
    private String region;

    @Value("${cloud.aws.s3.url}")
    private String s3EndpointUrl;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.access-key}")
    private String accessKey;

    @Value("${cloud.aws.s3.secret-key}")
    private String secretKey;


    @Bean(name = "amazonS3")
    public AmazonS3 amazonS3() {
        var amazonS3 = AmazonS3Client
                .builder()
                .withCredentials(getCredentialsProvider())
                .withEndpointConfiguration(getEndpointConfiguration(s3EndpointUrl))
                .withPathStyleAccessEnabled(false)
                .build();

        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }

        return amazonS3;
    }


    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(String url) {
        return new AwsClientBuilder.EndpointConfiguration(url, region);
    }

    private AWSStaticCredentialsProvider getCredentialsProvider() {
        return new AWSStaticCredentialsProvider(getBasicAWSCredentials());
    }

    private BasicAWSCredentials getBasicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}
