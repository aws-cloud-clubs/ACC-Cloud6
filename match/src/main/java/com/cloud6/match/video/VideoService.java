package com.cloud6.match.video;


import com.cloud6.match.match.MatchIndexEntry;
import com.cloud6.match.match.MatchQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private MatchQueueService matchQueueService;

    public Page<VideoDto> getAllInPage(int page, int size) {
        List<VideoDto> dtos = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);

        /*
        * page, size에 맞는 <video_id, waiting> 받기 - service method
        * iterator -> id 정보로 video entity에서 정보 받아와 dto 정보 구성
        * 구성된 dto들 response에 list로 넣어서 return
        * */
        List<MatchIndexEntry> redisEntries = matchQueueService.getQueueIds(page, size);
        for (MatchIndexEntry redisEntry : redisEntries) {
            Optional<Video> opVideo = videoRepository.findById(Long.parseLong(redisEntry.getVideo_id()));
            Video video = opVideo.orElseThrow();
            VideoDto dto = video.toDtoAddWaiting(redisEntry.getWaiting());
            dtos.add(dto);
        }
        return new PageImpl<>(dtos, pageRequest, size);
    }



}
