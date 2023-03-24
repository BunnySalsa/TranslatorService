package com.tinkoff.translator.repositories;

import com.tinkoff.translator.entities.TranslationResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class TranslationRepository {
    private static final int NUMBER_OF_QUERY_COLUMN = 1;
    private static final int NUMBER_OF_SOURCE_LANG_COLUMN = 2;
    private static final int NUMBER_OF_TARGET_LANG_COLUMN = 3;
    private static final int NUMBER_OF_SOURCE_TEXT_COLUMN = 4;
    private static final int NUMBER_OF_TARGET_TEXT_COLUMN = 5;
    private static final String PS_INSERT_RESULT = "INSERT INTO translator_scheme.translation" +
            "(request,source_lang,target_lang,source_text,target_text) VALUES (?,?,?,?,?)";
    private final Connection connection;

    public boolean save(TranslationResultEntity entity) {
        try (PreparedStatement statement = connection.prepareStatement(PS_INSERT_RESULT)) {
            statement.setLong(NUMBER_OF_QUERY_COLUMN, entity.getRequest());
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