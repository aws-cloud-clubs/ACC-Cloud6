package com.cloud6.match.video;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/video")
    public ResponseEntity<Page> getAllVideosInPage(@RequestParam Long page,
                                                   @RequestParam Long size,
                                                   @RequestParam Sort sort) {
        int pageNumber = page.intValue();
        int pageSize = size.intValue();

        Page<VideoDto> videosInPage = videoService.getAllInPage(pageNumber, pageSize, sort);

        return ResponseEntity.of(Optional.ofNullable(videosInPage));
    }
}
