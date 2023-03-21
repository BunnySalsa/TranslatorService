package com.tinkoff.translator.db.dao;

import com.tinkoff.translator.client.dto.TranslationDto;
import com.tinkoff.translator.db.entities.TranslationResult;
import com.tinkoff.translator.dto.IncomingMessageDto;
import org.springframework.jdbc.core.JdbcTemplate;

public class TranslationResultDao {


    public boolean save(IncomingMessageDto incoming, TranslationDto translation) {
        TranslationResult result = TranslationResult.builder().build();
        JdbcTemplate template = new JdbcTemplate();
        template.execute("");
        return true;
    }
}
