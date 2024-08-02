package com.cloud6.match.video;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "video")
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    private String thumbnail_url;
//    @Transient
//    private Long waiting;

}
