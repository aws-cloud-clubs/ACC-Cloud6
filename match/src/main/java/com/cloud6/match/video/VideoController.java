package com.cloud6.match.video;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                                                   @RequestParam Long size, @RequestParam List<String> sort,
                                                   @PageableDefault Pageable pageable) {
        int pageNumber = page.intValue();
        int pageSize = size.intValue();
        String title = sort.get(0);
        String sorting = sort.get(1);

        Page<VideoResponseDto> videosInPage = videoService.getAllInPage(pageNumber, pageSize, title, sorting, pageable);

        return ResponseEntity.of(Optional.ofNullable(videosInPage));

    }
}
