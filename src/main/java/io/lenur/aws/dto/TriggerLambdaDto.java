package io.lenur.aws.dto;

import lombok.Data;

import java.util.List;

@Data
public class TriggerLambdaDto {
    private List<RecordBody> Records;

    @Data
    private static class RecordBody {
        private String body;
    }
}
