package com.cloud6.match.match;

import java.util.List;

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
    private final MatchQueueService matchQueueService;
    
    @GetMapping("/match/order")
    public List<MatchIndexEntry> matchOrder(long startIndex, long size) {
        return matchQueueService.getQueueIds(startIndex, size);
    }

    @GetMapping("/match/init")
    public void initQueue() {
        matchQueueService.initQueueIndex(List.of());
    }

    @GetMapping("/test")
    public String getTest() {
        return template.opsForValue().get("test");
    }


    @PostMapping("/test")
    public void setTest(@RequestParam String value) {
        template.opsForValue().set("test", value);
    }
}

