package com.tinkoff.translator.client;

import java.util.concurrent.ExecutionException;

public interface TranslatorClient<S, R> {
    R translate(S dto) throws ExecutionException, InterruptedException;
}
