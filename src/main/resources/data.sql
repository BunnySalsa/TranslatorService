CREATE SCHEMA translator_scheme AUTHORIZATION sa;

USE translator_scheme;

CREATE TABLE translation_result (
    id bigint PRIMARY KEY,
    source_lang varchar(255) NOT NULL default 'unknown',
    target_lang varchar(255) NOT NULL default 'unknown',
    source_text varchar(10000) NOT NULL default 'unknown',
    target_text varchar(10000) NOT NULL default 'unknown',
    answer_time int NOT NULL,
    client_ip varchar(15) NOT NULL
);