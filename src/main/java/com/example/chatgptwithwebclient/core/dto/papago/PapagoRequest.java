package com.example.chatgptwithwebclient.core.dto.papago;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PapagoRequest {
    private String source;
    private String target;
    private String text;
}
