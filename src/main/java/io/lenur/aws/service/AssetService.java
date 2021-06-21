package io.lenur.aws.service;

import io.lenur.aws.config.ParamsConfig;
import io.lenur.aws.entity.Asset;
import io.lenur.aws.repository.AssetRepository;
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
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class AssetService {

    @Autowired
    private final S3Client s3Client;
    @Autowired
    private final ParamsConfig paramsConfig;
    @Autowired
    private final AssetRepository repository;

    public Asset upload(MultipartFile file) {
        var metadata = new HashMap<String, String>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        metadata.put("filename", file.getName());
        var key = UUID.randomUUID().toString();
        var objectRequest = PutObjectRequest.builder()
                .bucket(paramsConfig.getS3Bucket())
                .metadata(metadata)
                .key(key)
                .build();

        try {
            s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            LOGGER.error("The error has been occurred during putting the object to S3", e);
            throw new RuntimeException(e);
        }
        var asset = Asset.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .s3Key(key)
                .mimeType(file.getContentType())
                .build();

        return this.repository.save(asset);
    }
}
