package com.tinkoff.translator.controllers;

import com.tinkoff.translator.dto.MessageDto;
import com.tinkoff.translator.dto.TranslationDto;
import com.tinkoff.translator.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalTime;

@RestController
@RequestMapping("/translate")
public class TranslatorController {

    private TranslationService service;

    public TranslatorController(@Autowired TranslationService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<TranslationDto> translate(@RequestBody MessageDto message, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(service.serve(message, LocalTime.now().getNano()/1000, request.getRemoteAddr()));
        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
