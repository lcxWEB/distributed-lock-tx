package com.lcx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiChunxia
 * @Date 2021/1/25  15:00
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("set")
    public void setGet() {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        String value = stringRedisTemplate.opsForValue().get("aaa");
        System.out.println(value);
    }


}
