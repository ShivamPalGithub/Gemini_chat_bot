package com.Gemini_bot.controller;

import com.Gemini_bot.dto.AskQuestionRequest;
import com.Gemini_bot.dto.AskQuestionResponse;
import com.Gemini_bot.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/qna")
@Slf4j
@RequiredArgsConstructor
public class AiController {

    private final QnaService qnaService;

    @PostMapping("/ask")
    public ResponseEntity<AskQuestionResponse> askQuestion(@RequestBody AskQuestionRequest request) {
        String question = request.getQuestion();

        if (question == null || question.trim().isEmpty()) {
            log.warn("Received empty or null question.");
            return ResponseEntity.badRequest().body(new AskQuestionResponse("Question cannot be null or empty."));
        }

        try {
            String answer = qnaService.getAnswer(question);
            log.info("Answer retrieved successfully for question: {}", question);
            return ResponseEntity.ok(new AskQuestionResponse(answer));
        } catch (Exception e) {
            log.error("Error occurred while processing the question: {}", question, e);
            return ResponseEntity.status(500).body(new AskQuestionResponse("An error occurred while processing your request."));
        }
    }
}
