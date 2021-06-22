package io.lenur.aws.service;

import io.lenur.aws.config.S3Config;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.HashMap;

@Service
@Log4j2
@AllArgsConstructor
public class StorageService {
    @Autowired
    private final S3Config s3Config;

    @Autowired
    private final S3Client s3Client;

    public void upload(MultipartFile file, String key) {
        var metadata = new HashMap<String, String>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        metadata.put("filename", file.getName());

        var objectRequest = PutObjectRequest.builder()
                .bucket(s3Config.getS3Bucket())
                .metadata(metadata)
                .key(key)
                .build();

        try {
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            LOGGER.error("The error has been occurred during putting the object to S3", e);
            throw new RuntimeException(e);
        }
    }
}
