package com.example.chatgptwithwebclient.infra.http;

import com.example.chatgptwithwebclient.core.adaptor.ChatGptAdaptor;
import com.example.chatgptwithwebclient.core.dto.chatgpt.ChatGptRequest;
import com.example.chatgptwithwebclient.core.dto.chatgpt.ChatGptResponse;
import com.example.chatgptwithwebclient.infra.helper.WebClientHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ChatGptAdaptorImpl implements ChatGptAdaptor {
    private final WebClient webClient;

    @Autowired
    public ChatGptAdaptorImpl(@Value("${service.chatgpt.host:}") String serviceHost,
                              ObjectMapper objectMapper) {
        this.webClient = WebClientHelper.newWebClient(serviceHost, objectMapper);
    }

    @Override
    public Mono<ChatGptResponse> search(ChatGptRequest request, String apiKey) {
        return webClient.post()
            .uri("/v1/completions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", apiKey)
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError, ClientResponse::createException)
            .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
            .bodyToMono(new ParameterizedTypeReference<ChatGptResponse>() {})
            .doOnError(throwable -> log.error("chat gpt api fail. exception={}", throwable.getMessage()));
    }
}
