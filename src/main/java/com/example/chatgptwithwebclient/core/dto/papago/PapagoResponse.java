package com.example.chatgptwithwebclient.core.dto.papago;

import lombok.Data;

@Data
public class PapagoResponse {
    PapagoMessageDto message;

    @Data
    public static class PapagoMessageDto {
        String type;
        String service;
        String version;
        PapagoResult result;
    }

    @Data
    public static class PapagoResult {
        String srcLangType;
        String tarLangType;
        String translatedText;
    }
}
