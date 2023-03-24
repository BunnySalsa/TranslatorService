package com.tinkoff.translator.controllers;

import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/translate")
public class TranslatorController {
    private final TranslationService service;


    @PostMapping
    public ResponseEntity translate(@RequestBody MessageDto message, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(service.translate(message,
                    request.getRemoteAddr()));
        } catch (ExecutionException | InterruptedException exception) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().build();
        } catch (RestClientException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

}
