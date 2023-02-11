package com.example.chatgptwithwebclient.application;

import com.example.chatgptwithwebclient.core.adaptor.ChatGptAdaptor;
import com.example.chatgptwithwebclient.core.adaptor.TranslationAdaptor;
import com.example.chatgptwithwebclient.core.dto.chatgpt.ChatGptRequest;
import com.example.chatgptwithwebclient.core.dto.client.SearchDramaScenarioRequest;
import com.example.chatgptwithwebclient.core.dto.client.SearchDramaScenarioResponse;
import com.example.chatgptwithwebclient.core.dto.client.SearchItineraryRequest;
import com.example.chatgptwithwebclient.core.dto.client.SearchItineraryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGptService {
    private final ChatGptAdaptor chatGptAdaptor;
    private final TranslationAdaptor translationAdaptor;

    public Mono<SearchItineraryResponse> searchItinerary(SearchItineraryRequest request) {
        String basePrompt = String.format("what is an ideal itinerary for %d days in %s?",
                                          request.getDays(),
                                          request.getCity());


        final ChatGptRequest chatGptRequest = ChatGptRequest.builder()
            .model("text-davinci-003")
            .prompt(basePrompt)
            .temperature(0)
            .maxTokens(550)
            .build();

        return chatGptAdaptor.search(chatGptRequest, request.getOpenApiKey())
            .flatMap(chatGptResponse -> translationAdaptor.translateEngToKor(
                chatGptResponse.getChoices().get(0).getText(),
                request.getNaverClientId(),
                request.getNaverClientSecret())
            ).map(translatedStr -> SearchItineraryResponse.builder()
                .result(translatedStr)
                .build()
            );

    }

    public Mono<SearchDramaScenarioResponse> searchDramaScenario(SearchDramaScenarioRequest request) {
        final ChatGptRequest chatGptRequest = ChatGptRequest.builder()
            .model("text-davinci-003")
            .prompt(String.format("Please recommend {} out of many jobs in the world.", request.getCharactersCount()))
            .temperature(0)
            .maxTokens(550)
            .build();

        log.info("chat gpt job request={}", chatGptRequest);

        return chatGptAdaptor.search(chatGptRequest, request.getOpenApiKey())
            .flatMap(response -> {
                String basePrompt = String.format("Please write a script for a %s drama consisting of %d episodes " +
                                                  "with male lead: %s, female lead: %s and %d characters. %d characters job is %s",
                                                  request.getGenre(),
                                                  request.getEpisodeCount(),
                                                  request.getMaleLeadName(),
                                                  request.getFemaleLeadName(),
                                                  request.getCharactersCount(),
                                                  request.getCharactersCount(),
                                                  response.getChoices().get(0).getText());


                final ChatGptRequest scenarioRequest = ChatGptRequest.builder()
                    .model("text-davinci-003")
                    .prompt(basePrompt)
                    .temperature(0)
                    .maxTokens(550)
                    .build();

                log.info("chat gpt scenario request={}", chatGptRequest);

                return chatGptAdaptor.search(scenarioRequest, request.getOpenApiKey())
                    .flatMap(chatGptResponse -> translationAdaptor.translateEngToKor(
                        chatGptResponse.getChoices().get(0).getText(),
                        request.getNaverClientId(),
                        request.getNaverClientSecret())
                    ).map(translatedStr -> SearchDramaScenarioResponse.builder()
                        .result(translatedStr)
                        .build()
                    );
            });
    }

//    public OpenAiSearchPointOfInterestResponse searchPointOfInterest(OpenAiSearchPointOfInterestRequest request) {
//        final OpenAiRequest openAiRequest = OpenAiRequest.builder()
//            .model("text-davinci-003")
//            .prompt(request.getPrompt())
//            .temperature(0)
//            .max_tokens(300)
//            .build();
//
//        final OpenAiResponse search = chatGptAdaptor.search(openAiRequest);
//
//        log.info("open ai search point of interest={}", search);
//
//        // Tower of London, South Bank of the River Thames, Tate Modern, British Museum, National Gallery, Victoria and Albert Museum, Windsor Castle, River Thames, Covent Garden, Oxford Street, Camden Market, West End.
//        final String pointOfInterest = search.getChoices().get(0).getText();
//
//        final List<String> pointsOfInterest = Arrays.stream(pointOfInterest.split(","))
//            .map(String::trim)
//            .collect(Collectors.toList());
//
//        return OpenAiSearchPointOfInterestResponse.builder()
//            .pointsOfInterest(pointsOfInterest)
//            .build();
//    }
}
