package com.example.chatgptwithwebclient.core.adaptor;

import reactor.core.publisher.Mono;

public interface TranslationAdaptor {
    Mono<String> translateEngToKor(String eng, String apiId, String apiSecret);

    Mono<String> translateKorToEng(String kor, String apiId, String apiSecret);
}
