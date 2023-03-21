package com.tinkoff.translator.db.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "translation_result")
public class TranslationResult {
    @Id
    @Getter
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    /**
     * Empty constructor for successful serialization
     */
    public TranslationResult() {
    }
}
