package com.Gemini_bot.service;

import com.Gemini_bot.dto.AskQuestionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QnaService {

    @Value("${gemini.api.url}")
    private String geminiURL;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public QnaService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getAnswer(String question) {
        log.info("Received question: {}", question);

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", question)
                                )
                        )
                )
        );

        try {
            return webClient.post()
                    .uri(geminiURL + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(this::extractText)
                    .block();
        } catch (Exception e) {
            log.error("Error occurred while sending the request to the Gemini API", e);
            return "{\"error\": \"Error occurred while processing the request.\"}";
        }
    }

    private String extractText(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                return candidates.get(0)
                        .getOrDefault("content", Map.of())
                        .toString();
            }
        } catch (Exception e) {
            log.error("Error parsing response: ", e);
        }
        return "{\"error\": \"No valid response received.\"}";
    }
}
