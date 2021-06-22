package io.lenur.aws.controller;

import io.lenur.aws.entity.Asset;
import io.lenur.aws.service.AssetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/assets")
@AllArgsConstructor
public class AssetController {

    @Autowired
    private final AssetService assetService;

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Asset> upload(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(assetService.upload(file), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getList() {
        return new ResponseEntity<>(assetService.getList(), HttpStatus.OK);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Asset> getByKey(@PathVariable String key) {
        return new ResponseEntity<>(assetService.getByKey(key), HttpStatus.OK);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<?> deleteItem(@PathVariable String key) {
        assetService.delete(key);
        return ResponseEntity.ok().build();
    }
}
