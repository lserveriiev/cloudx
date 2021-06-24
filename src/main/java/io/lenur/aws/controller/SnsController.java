package io.lenur.aws.controller;

import io.lenur.aws.dto.SubscribeSnsDto;
import io.lenur.aws.service.SnsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sns")
@AllArgsConstructor
public class SnsController {
    @Autowired
    private final SnsService snsService;

    @PostMapping(path = "/subscribe")
    public ResponseEntity<?> upload(@RequestBody SubscribeSnsDto subscribeSnsDto) {
        snsService.subscribe(subscribeSnsDto.getEmail());
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
