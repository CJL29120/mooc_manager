package com.chen.mooc_manager.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * String cache store.
 *
 * @author johnniang
 */
@Slf4j
@Component()
public abstract class StringCacheStore extends AbstractCacheStore<String, String> {

    public <T> void putAny(String key, T value) {
        try {
            put(key, JSONObject.toJSONString(value));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to convert " + value + " to json", e);
        }
    }

    public <T> void putAny(@NonNull String key, @NonNull T value, long timeout, @NonNull TimeUnit timeUnit) {
        try {
            put(key, JSONObject.toJSONString(value), timeout, timeUnit);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to convert " + value + " to json", e);
        }
    }

    public <T> Optional<T> getAny(String key, Class<T> type) {
        Assert.notNull(type, "Type must not be null");

        return get(key).map(value -> {
            try {
                return JSONObject.parseObject(value, type);
            } catch (RuntimeException e) {
                log.error("Failed to convert json to type: " + type.getName(), e);
                return null;
            }
        });
    }
}
