package com.cloud6.match.video;

import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class VideoResponseDto {
    private Long video_id;
    private String title;
    private Long thumbnail_url;
    private Long description;
    private Long waiting;

}
