CREATE SCHEMA translator_scheme AUTHORIZATION sa;

USE translator_scheme;

CREATE TABLE request (
    id bigint auto_increment PRIMARY KEY,
    answer_time int NOT NULL,
    client_ip varchar(15) NOT NULL
);

CREATE TABLE translation (
    id bigint auto_increment,
    request bigint,
    source_lang varchar(2) NOT NULL default 'unknown',
    target_lang varchar(2) NOT NULL default 'unknown',
    source_text varchar(10000) NOT NULL default 'unknown',
    target_text varchar(10000) NOT NULL default 'unknown',
    CONSTRAINT pk_composite_key PRIMARY KEY (id, request),
    CONSTRAINT fk_query FOREIGN KEY (request) REFERENCES request(id)
);


