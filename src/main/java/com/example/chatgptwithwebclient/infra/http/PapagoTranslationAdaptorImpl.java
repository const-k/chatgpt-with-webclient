package com.example.chatgptwithwebclient.infra.http;

import com.example.chatgptwithwebclient.core.adaptor.TranslationAdaptor;
import com.example.chatgptwithwebclient.core.dto.papago.PapagoRequest;
import com.example.chatgptwithwebclient.core.dto.papago.PapagoResponse;
import com.example.chatgptwithwebclient.infra.helper.WebClientHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PapagoTranslationAdaptorImpl implements TranslationAdaptor {
    private final WebClient webClient;


    @Autowired
    public PapagoTranslationAdaptorImpl(@Value("${service.papago.host:}") String serviceHost,
                                        ObjectMapper objectMapper) {
        webClient = WebClientHelper.newWebClient(serviceHost, objectMapper);
    }

    @Override
    public Mono<String> translateEngToKor(String eng, String apiId, String apiSecret) {
        PapagoRequest request = new PapagoRequest("en", "ko", eng);

        log.info("papago translate request={}", eng);

        return getTranslatedResult(apiId, apiSecret, request);
    }

    @Override
    public Mono<String> translateKorToEng(String kor, String apiId, String apiSecret) {
        PapagoRequest request = new PapagoRequest("ko", "en", kor);

        log.info("papago translate request={}", kor);

        return getTranslatedResult(apiId, apiSecret, request);
    }

    private Mono<String> getTranslatedResult(String apiId, String apiSecret, PapagoRequest request) {
        return webClient.post()
            .uri("v1/papago/n2mt")
            .header("X-Naver-Client-Id", apiId)
            .header("X-Naver-Client-Secret", apiSecret)
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError, ClientResponse::createException)
            .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
            .bodyToMono(PapagoResponse.class)
            .map(response -> response.getMessage().getResult().getTranslatedText())
            .doOnError(throwable -> log.error("papago api fail. exception={}", throwable.getMessage()));
    }

}
