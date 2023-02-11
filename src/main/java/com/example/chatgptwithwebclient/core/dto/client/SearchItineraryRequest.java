package com.example.chatgptwithwebclient.core.dto.client;

import lombok.Data;

@Data
public class SearchItineraryRequest {
    private int days;
    private String city;
    private String openApiKey;
    private String naverClientId;
    private String naverClientSecret;
}
