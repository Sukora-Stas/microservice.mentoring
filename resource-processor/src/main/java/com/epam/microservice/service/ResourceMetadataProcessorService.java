package com.epam.microservice.service;

import com.epam.microservice.service.exception.ParseMetadataException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import com.epam.microservice.model.SongMetadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceMetadataProcessorService {

    public SongMetadata retrieveResourceMetadata(byte[] fileContent, Long resourceId) {
        try (InputStream input = new ByteArrayInputStream(fileContent)) {
            ContentHandler contentHandler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseContext = new ParseContext();
            parser.parse(input, contentHandler, metadata, parseContext);
            return SongMetadata.builder()
                    .album(metadata.get(XMPDM.ALBUM))
                    .artist(metadata.get(XMPDM.ARTIST))
                    .length(metadata.get(XMPDM.DURATION))
                    .name(metadata.get(TikaCoreProperties.TITLE))
                    .resourceId(resourceId)
                    .year(metadata.get(XMPDM.RELEASE_DATE))
                    .build();
        } catch (IOException | SAXException | TikaException e) {
            throw new ParseMetadataException(resourceId, e);
        }
    }
}
