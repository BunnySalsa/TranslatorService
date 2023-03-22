package com.tinkoff.translator.db.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "translation_result")
public class TranslationResult {
    @Id
    @Getter
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "source_lang")
    private String sourceLang;
    @Column(name = "target_lang")
    private String targetLang;
    @Column(name = "source_text")
    private String sourceText;
    @Column(name = "target_text")
    private String targetText;
    @Column(name = "answer_time")
    private int answerTime;
    @Column(name = "client_ip")
    private String clientIp;
}
