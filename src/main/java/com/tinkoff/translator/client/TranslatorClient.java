package com.tinkoff.translator.client;

public interface TranslatorClient<S, R> {
    R translate(S dto) throws InterruptedException;
}
