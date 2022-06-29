package com.epam.microservice.service;

import com.epam.microservice.client.ResourceServiceClient;
import com.epam.microservice.client.SongServiceClient;
import com.epam.microservice.service.exception.ParseMetadataException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import com.epam.microservice.model.SongMetadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceMetadataProcessorService {

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    @Autowired
    private SongServiceClient songServiceClient;

    public SongMetadata extractMetadata(Long resourceId, InputStream data) {
        var autoDetectParser = new AutoDetectParser();
        var handler = new BodyContentHandler();
        var metadata = new Metadata();
        var context = new ParseContext();

        try {
            autoDetectParser.parse(data, handler, metadata, context);
        } catch (IOException | SAXException | TikaException ex) {
            throw new ParseMetadataException(resourceId, ex);
        }

        return new SongMetadata(
                metadata.get(XMPDM.ALBUM),
                metadata.get(XMPDM.ARTIST),
                metadata.get(XMPDM.DURATION),
                metadata.get(TikaCoreProperties.TITLE),
                resourceId,
                metadata.get(XMPDM.RELEASE_DATE));
    }

    public SongMetadata extractAndSaveMetadata(Long resourceId) throws IOException {
        var data = resourceServiceClient.getResourceBinaryData(resourceId);
        var song = extractMetadata(resourceId, data);

        if (!songServiceClient.songMetadataExists(resourceId)) {
            songServiceClient.persistMetadata(song);
        }

        return song;
    }


}
