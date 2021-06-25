package io.lenur.aws.service;

import io.lenur.aws.entity.Asset;
import io.lenur.aws.exception.AssetNotFoundException;
import io.lenur.aws.repository.AssetRepository;
import io.lenur.aws.service.storage.Storageble;
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
    private final Storageble storage;
    @Autowired
    private final AssetRepository repository;
    @Autowired
    private final SqsService sqsService;

    public Asset upload(MultipartFile file) {
        var key = UUID.randomUUID().toString();
        storage.upload(file, key);
        var asset = Asset.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .s3Key(key)
                .mimeType(file.getContentType())
                .build();

        var assetPersist = this.repository.save(asset);
        sqsService.sendMessage(String.valueOf(assetPersist.getId()));
        return assetPersist;
    }

    public List<Asset> getList() {
        return this.repository.findAll();
    }

    public Asset getByKey(String key) {
        return this.repository
                .getByS3Key(key)
                .orElseThrow(AssetNotFoundException::new);
    }

    public Asset getById(Long id) {
        return this.repository.getById(id);
    }

    public void delete(String key) {
        var asset = this.getByKey(key);
        storage.delete(key);
        this.repository.delete(asset);
    }

    public byte[] download(String key) {
        var asset = this.getByKey(key);
        return storage.download(asset.getS3Key());
    }
}
