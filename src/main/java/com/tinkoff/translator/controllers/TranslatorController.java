package com.tinkoff.translator.controllers;

import com.tinkoff.translator.client.YandexTranslatorClient;
import com.tinkoff.translator.client.dto.MessageDto;
import com.tinkoff.translator.client.dto.TranslationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/translate")
public class TranslatorController {
    YandexTranslatorClient client;

    public TranslatorController(@Autowired YandexTranslatorClient client) {
        this.client = client;
    }


    @PostMapping("/{srcLang}/{targetLang}")
    public ResponseEntity<TranslationDto> translate(@PathVariable String srcLang, @PathVariable String targetLang,
                                                    @RequestBody String message) {
        MessageDto messageDto = MessageDto.builder()
                .folderId("b1gkd141l14iovkfjeif")
                .sourceLang(srcLang)
                .targetLang(targetLang)
                .texts(List.of(message)).build();
        TranslationDto result = client.translate(messageDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{targetLang}")
    public ResponseEntity<TranslationDto> translate(@PathVariable String targetLang,
                                                    @RequestBody String message) {
        MessageDto messageDto = MessageDto.builder()
                .folderId("b1gkd141l14iovkfjeif")
                .targetLang(targetLang)
                .texts(List.of(message)).build();
        TranslationDto result = client.translate(messageDto);
        return ResponseEntity.ok(result);
    }

}
