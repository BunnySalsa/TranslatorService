package com.tinkoff.translator.db.dao;

import com.tinkoff.translator.client.dto.YaTranslationDto;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.dto.MessageDto;
import org.springframework.jdbc.core.JdbcTemplate;

public class TranslationResultDao {


    public boolean save(MessageDto incoming, YaTranslationDto translation) {
        TranslationResult result = TranslationResult.builder().build();
        JdbcTemplate template = new JdbcTemplate();
        template.execute("");
        return true;
    }
}
