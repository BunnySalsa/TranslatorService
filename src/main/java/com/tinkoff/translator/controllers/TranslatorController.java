package com.tinkoff.translator.controllers;

import com.tinkoff.translator.client.dto.ExceptionDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.badRequest().body(exception.getResponseBodyAs(ExceptionDto.class));
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().build();
        }
    }

}
