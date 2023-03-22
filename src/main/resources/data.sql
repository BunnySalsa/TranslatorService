CREATE SCHEMA translator_scheme AUTHORIZATION sa;

USE translator_scheme;

CREATE TABLE translation (
    id bigint auto_increment PRIMARY KEY,
    source_lang varchar(255) NOT NULL default 'unknown',
    target_lang varchar(255) NOT NULL default 'unknown',
    source_text varchar(10000) NOT NULL default 'unknown',
    target_text varchar(10000) NOT NULL default 'unknown'
);

CREATE TABLE query (
    id bigint auto_increment PRIMARY KEY,
    answer_time int NOT NULL,
    client_ip varchar(15) NOT NULL
);

CREATE TABLE query_translation (
    query_id bigint NOT NULL,
    translation_id NOT NULL,
    CONSTRAINT fk_query_id FOREIGN KEY (query_id) REFERENCES query(id),
    CONSTRAINT fk_translation_id FOREIGN KEY (translation_id) REFERENCES translation(id),
    CONSTRAINT pk_composite_key PRIMARY KEY (query_id, translation_id)
);
