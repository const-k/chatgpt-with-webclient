package com.example.chatgptwithwebclient.presentation.controller;

import com.example.chatgptwithwebclient.application.ChatGptService;
import com.example.chatgptwithwebclient.core.dto.chatgpt.ChatGptResponse;
import com.example.chatgptwithwebclient.core.dto.client.SearchDramaScenarioRequest;
import com.example.chatgptwithwebclient.core.dto.client.SearchDramaScenarioResponse;
import com.example.chatgptwithwebclient.core.dto.client.SearchItineraryRequest;
import com.example.chatgptwithwebclient.core.dto.client.SearchItineraryResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatgpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "여행지 명(영어), 기간을 입력하면 한글로 여행 일정을 알려주는 api 입니다.")
    @PostMapping("/itinerary")
    public Mono<SearchItineraryResponse> searchItinerary(@RequestBody SearchItineraryRequest request) {
        return chatGptService.searchItinerary(request);
    }

    @ApiOperation(value = "드라마 장르 / 남자, 여자 주인공 이름 / 등장인물 수, 에피소드 수 (영어)를 입력하면 한글로 시나리오를 써주는 api 입니다.")
    @PostMapping("/scenario")
    public Mono<SearchDramaScenarioResponse> searchDramaScenario(@RequestBody SearchDramaScenarioRequest request) {
        return chatGptService.searchDramaScenario(request);
    }
}
