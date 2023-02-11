package com.example.chatgptwithwebclient.core.dto.client;

import lombok.Data;

@Data
public class SearchDramaScenarioRequest {
    private String maleLeadName;
    private String femaleLeadName;
    private int charactersCount;
    private int episodeCount;
    private String genre;
    private String openApiKey;
    private String naverClientId;
    private String naverClientSecret;
}
