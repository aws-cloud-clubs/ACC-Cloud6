package com.cloud6.match.match;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MatchController {
private final MatchQueueService matchQueueService;
    
    @GetMapping("/match/order")
    public List<MatchIndexEntry> matchOrder(long startIndex, long size) {
        return matchQueueService.getQueueIds(startIndex, size);
    }
}

