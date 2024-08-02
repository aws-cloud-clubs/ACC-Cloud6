package com.cloud6.match.video;

import com.cloud6.match.match.MatchQueueService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VideoInitService implements SmartLifecycle {
    @Autowired
    private MatchQueueService matchQueueService;
    @Autowired
    private VideoRepository videoRepository;

    @Override
    public void start() {
        List<Video> allVideos = videoRepository.findAll();
        System.out.println(allVideos.size());
        List<String> allIds = allVideos.stream().map(v -> v.getId().toString()).collect(Collectors.toList());
        matchQueueService.initQueueIndex(allIds);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
