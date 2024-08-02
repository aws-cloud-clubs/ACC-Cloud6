package com.cloud6.match.match;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedisTestController {

    private final StringRedisTemplate template;

    @GetMapping("/test")
    public String getTest() {
        return template.opsForValue().get("test");
    }


    @PostMapping("/test")
    public void setTest(@RequestParam String value) {
        template.opsForValue().set("test", value);
    }
}

