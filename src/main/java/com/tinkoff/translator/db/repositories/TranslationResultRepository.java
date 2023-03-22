package com.tinkoff.translator.db.repositories;

import com.tinkoff.translator.db.entities.TranslationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TranslationResultRepository {
    private static final int NUMBER_OF_SOURCE_LANG_COLUMN = 1;
    private static final int NUMBER_OF_TARGET_LANG_COLUMN = 2;
    private static final int NUMBER_OF_SOURCE_TEXT_COLUMN = 3;
    private static final int NUMBER_OF_TARGET_TEXT_COLUMN = 4;
    private static final String PS_INSERT_RESULT = "INSERT INTO translator_scheme.translation_result" +
            "(source_lang,target_lang,source_text,target_text) VALUES (?,?,?,?)";
    private RowMapper<TranslationResult> rowMapper;
    private Connection connection;

    public TranslationResultRepository(@Autowired RowMapper<TranslationResult> rowMapper,
                                       @Autowired Connection connection) {
        this.rowMapper = rowMapper;
        this.connection = connection;
    }


    public boolean save(TranslationResult entity) {
        try (PreparedStatement statement = connection.prepareStatement(PS_INSERT_RESULT)) {
            statement.setString(NUMBER_OF_SOURCE_LANG_COLUMN, entity.getSourceLang());
            statement.setString(NUMBER_OF_TARGET_LANG_COLUMN, entity.getTargetLang());
            statement.setString(NUMBER_OF_SOURCE_TEXT_COLUMN, entity.getSourceText());
            statement.setString(NUMBER_OF_TARGET_TEXT_COLUMN, entity.getTargetText());
            return statement.execute();
        } catch (SQLException exception) {
            return false;
        }
    }
}
