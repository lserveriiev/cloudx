package io.lenur.aws.service;

import io.lenur.aws.config.AwsConfig;
import io.lenur.aws.dto.TriggerLambdaDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import com.google.gson.Gson;

@Service
@AllArgsConstructor
@Log4j2
public class LambdaService {

    @Autowired
    private final LambdaClient lambdaClient;
    @Autowired
    private final AwsConfig awsConfig;
    @Autowired
    private final Gson gson;

    public void invokeFunction(TriggerLambdaDto lambdaDto) {
        var request = InvokeRequest.builder()
                .functionName(awsConfig.getLambdaFunctionName())
                .payload(SdkBytes.fromUtf8String(gson.toJson(lambdaDto)))
                .build();
        lambdaClient.invoke(request);
    }
}
