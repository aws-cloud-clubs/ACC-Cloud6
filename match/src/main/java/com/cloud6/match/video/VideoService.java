package com.cloud6.match.video;

import com.cloud6.match.MatchQueueService;
import com.cloud6.match.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private MatchQueueService matchQueueService;
    @Autowired
    private RedisRepository redisRepository;

    public Page<VideoResponseDto> getAllInPage(int page, int size, String title, String sort, Pageable pageable) {

        PageRequest pageRequest = PageRequest.of(page, size);



    }



}
