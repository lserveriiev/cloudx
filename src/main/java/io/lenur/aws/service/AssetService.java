package io.lenur.aws.service;

import io.lenur.aws.entity.Asset;
import io.lenur.aws.exception.AssetNotFoundException;
import io.lenur.aws.repository.AssetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    public List<Asset> getList() {
        return this.repository.findAll();
    }

    public Asset getByKey(String key) {
        return this.repository
                .getAssetByKey(key)
                .orElseThrow(AssetNotFoundException::new);
    }

    public void delete(String key) {
        var asset = this.getByKey(key);
        storageService.delete(key);
        this.repository.delete(asset);
    }
}
