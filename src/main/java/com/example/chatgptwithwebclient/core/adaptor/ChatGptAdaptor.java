package com.example.chatgptwithwebclient.core.adaptor;

import com.example.chatgptwithwebclient.core.dto.chatgpt.ChatGptRequest;
import com.example.chatgptwithwebclient.core.dto.chatgpt.ChatGptResponse;
import reactor.core.publisher.Mono;

public interface ChatGptAdaptor {
    Mono<ChatGptResponse> search(ChatGptRequest request, String apiKey);
}
