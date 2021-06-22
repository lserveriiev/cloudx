package io.lenur.aws.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface Storageble {
    void delete(String key);

    void upload(MultipartFile file, String key);

    byte[] download(String key);
}
