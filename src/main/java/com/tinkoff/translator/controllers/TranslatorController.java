package com.tinkoff.translator.controllers;

import com.tinkoff.translator.client.TranslatorClient;
import com.tinkoff.translator.client.dto.YaMessageDto;
import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translate")
public class TranslatorController {

    private TranslationService service;

    public TranslatorController(@Autowired TranslationService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<TranslationDto> translate(@RequestBody MessageDto message) {
        return ResponseEntity.ok(service.serve(message));
    }

}
