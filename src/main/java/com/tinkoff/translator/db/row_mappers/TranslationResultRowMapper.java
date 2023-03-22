package com.tinkoff.translator.db.row_mappers;

import com.tinkoff.translator.db.entities.TranslationResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TranslationResultRowMapper implements RowMapper<TranslationResult> {
    private static final String ID_FIELD = "id";
    private static final String SOURCE_LANG_FIELD = "source_lang";
    private static final String TARGET_LANG_FIELD = "target_lang";
    private static final String SOURCE_TEXT_FIELD = "source_text";
    private static final String TARGET_TEXT_FIELD = "target_text";

    @Override
    public TranslationResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TranslationResult.builder()
                .id(rs.getLong(ID_FIELD))
                .sourceLang(rs.getString(SOURCE_LANG_FIELD))
                .targetLang(rs.getString(TARGET_LANG_FIELD))
                .sourceText(rs.getString(SOURCE_TEXT_FIELD))
                .targetText(rs.getString(TARGET_TEXT_FIELD)).build();
    }
}
