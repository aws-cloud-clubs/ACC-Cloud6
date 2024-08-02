package com.cloud6.match.video;

import com.cloud6.match.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String video_url;
    private String thumbnail_url;
    private long waiting;
}

