package com.tinkoff.translator.controllers;

import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.time.LocalTime;

@RestController
@RequestMapping("/translate")
public class TranslatorController {
    private static final int TO_MILLISECONDS_DIVIDER = 1000000;

    private TranslationService service;

    public TranslatorController(@Autowired TranslationService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity translate(@RequestBody MessageDto message, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(service.serve(message, TO_MILLISECONDS_DIVIDER,
                    LocalTime.now().getNano(),
                    request.getRemoteAddr()));
        } catch (RestClientException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
