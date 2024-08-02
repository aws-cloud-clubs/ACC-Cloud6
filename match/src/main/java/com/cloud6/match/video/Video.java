package com.cloud6.match.video;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "youtube")
public class Video {
    @Id
    private Long id;
    private String title;
    private String video_url;
    private String thumbnail_url;

    public VideoDto toDtoAddWaiting(long waiting) {
        return new VideoDto(this.id, this.title, this.video_url, this.thumbnail_url, waiting);
    }
}


