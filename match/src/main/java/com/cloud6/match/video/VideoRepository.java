package com.cloud6.match.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, String> {

//    Page<Video> findAllByIdOrderByTitle(List<String> ids,Pageable pageable);
}
