package io.lenur.aws.controller;

import io.lenur.aws.dto.TriggerLambdaDto;
import io.lenur.aws.service.LambdaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lambda")
@AllArgsConstructor
public class LambdaController {
    @Autowired
    private final LambdaService lambdaService;

    @PostMapping(path = "/trigger")
    public ResponseEntity<?> trigger(@RequestBody TriggerLambdaDto triggerLambdaDto) {
        lambdaService.invokeFunction(triggerLambdaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
