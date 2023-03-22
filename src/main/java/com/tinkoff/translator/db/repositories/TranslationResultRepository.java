package com.tinkoff.translator.db.repositories;

import com.tinkoff.translator.db.entities.TranslationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class TranslationResultRepository {
    private static final int NUMBER_OF_ID_COLUMN = 1;
    private static final int NUMBER_OF_SOURCE_LANG_COLUMN = 2;
    private static final int NUMBER_OF_TARGET_LANG_COLUMN = 3;
    private static final int NUMBER_OF_SOURCE_TEXT_COLUMN = 4;
    private static final int NUMBER_OF_TARGET_TEXT_COLUMN = 5;
    private static final int NUMBER_OF_ANSWER_TIME_COLUMN = 6;
    private static final int NUMBER_OF_CLIENT_IP_COLUMN = 7;
    private static final String PS_INSERT_RESULT = "INSERT INTO translator_scheme.translation_result" +
            "(source_lang, target_lang,source_text,target_text,answer_time,client_ip) VALUES (?,?,?,?,?,?)";
    private RowMapper<TranslationResult> rowMapper;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public TranslationResultRepository(@Autowired RowMapper<TranslationResult> rowMapper) {
        this.rowMapper = rowMapper;
    }


    public boolean save(TranslationResult entity) throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        PreparedStatement statement = connection.prepareStatement(PS_INSERT_RESULT);
        statement.setString(NUMBER_OF_SOURCE_LANG_COLUMN - 1, entity.getSourceLang());
        statement.setString(NUMBER_OF_TARGET_LANG_COLUMN - 1, entity.getTargetLang());
        statement.setString(NUMBER_OF_SOURCE_TEXT_COLUMN - 1, entity.getSourceText());
        statement.setString(NUMBER_OF_TARGET_TEXT_COLUMN - 1, entity.getTargetText());
        statement.setInt(NUMBER_OF_ANSWER_TIME_COLUMN - 1, entity.getAnswerTime());
        statement.setString(NUMBER_OF_CLIENT_IP_COLUMN - 1, entity.getClientIp());
        return statement.execute();
    }
}
