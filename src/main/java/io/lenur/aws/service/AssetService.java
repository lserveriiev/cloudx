package io.lenur.aws.service;

import io.lenur.aws.entity.Asset;
import io.lenur.aws.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AssetService {

    @Autowired
    private final StorageService storageService;

    @Autowired
    private final AssetRepository repository;

    public Asset upload(MultipartFile file) {
        var key = UUID.randomUUID().toString();
        storageService.upload(file, key);
        var asset = Asset.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .s3Key(key)
                .mimeType(file.getContentType())
                .build();

        return this.repository.save(asset);
    }
}
