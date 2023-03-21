package com.tinkoff.translator.controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/translate")
public class TranslatorController {


    @PostMapping("/{srcLang}/{trgtLang}")
    public HttpStatusCode translate(@PathVariable String srcLang, @PathVariable String trgtLang, @RequestBody String message) {
        System.out.println(message);


        return HttpStatusCode.valueOf(200);
    }

}
