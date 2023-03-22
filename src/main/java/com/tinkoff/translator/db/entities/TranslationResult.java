package com.tinkoff.translator.db.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationResult {

    private Long id;
    private String sourceLang;
    private String targetLang;
    private String sourceText;
    private String targetText;
}
