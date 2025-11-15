package com.canhxuan.CanhXuan_Building.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToRedis(String token, String value, int timeToLive) {
        redisTemplate.opsForValue().set(token, value, timeToLive, TimeUnit.MINUTES);
    }

//    public void saveAccessToken(String token, String value, int timeToLive) {
//        redisTemplate.opsForValue().set("access: " + token, value, timeToLive, TimeUnit.MINUTES);
//    }
//
//    public void saveRefreshToken(String token, String value, int timeToLive) {
//        redisTemplate.opsForValue().set("refresh: " + token, value, timeToLive, TimeUnit.MINUTES);
//    }

    public void blackListToken(String token, int timeToLive) {
        redisTemplate.opsForValue().set("blacklist: " + token, "true", timeToLive, TimeUnit.MINUTES);
    }

    public boolean isBlackListed(String token) {
        return redisTemplate.hasKey("blacklist: " + token);
    }


}
