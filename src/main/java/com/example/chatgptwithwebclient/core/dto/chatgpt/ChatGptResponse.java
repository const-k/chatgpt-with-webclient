package com.example.chatgptwithwebclient.core.dto.chatgpt;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatGptResponse {
    private String id;
    private String object;
    private long created;
    private List<Choice> choices;
    private Usage usage;

    @Getter
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Choice {
        private String text;
        private int index;
        private String logprobs;
        private String finishReason;
    }

    @Getter
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Usage {
        private String promptTokens;
        private String completionTokens;
        private String totalTokens;
    }
}
