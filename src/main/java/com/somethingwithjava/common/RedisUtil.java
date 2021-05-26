package com.somethingwithjava.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

@Slf4j
@Component
public class RedisUtil {
    private static Jedis jedis = new Jedis();

    public static synchronized String saveString(String key, String value) {
        return jedis.set(key, value);
    }

    public static synchronized String getByKey(String key) {
        return jedis.get(key);
    }

    public static synchronized boolean isExistValue(String key) {
        return StringUtils.hasText(jedis.get(key));
    }
}
