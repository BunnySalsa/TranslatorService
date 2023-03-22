package com.tinkoff.translator.config;

import lombok.Data;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class TokenScopeConfigurer implements Scope {
    private static final long LIFECYCLE_HOURS = 12;
    private Map<String, Pair<LocalTime, Object>> scopedObjects = new HashMap<>();
    private Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (scopedObjects.containsKey(name)) {
            Pair<LocalTime, Object> pair = scopedObjects.get(name);
            if (Duration.between(pair.getFirst(), LocalTime.now()).toHours() < LIFECYCLE_HOURS) {
                return scopedObjects.get(name).getSecond();
            }
        }
        scopedObjects.put(name, new Pair<>(LocalTime.now(), objectFactory.getObject()));
        return scopedObjects.get(name).getSecond();
    }

    @Override
    public Object remove(String name) {
        destructionCallbacks.remove(name);
        return scopedObjects.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        destructionCallbacks.put(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "token";
    }

    @Data
    private class Pair<K, V> {
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }
}
