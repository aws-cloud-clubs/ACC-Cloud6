package com.cloud6.match.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

//    Page<Video> findAllByIdOrderByTitle(List<String> ids,Pageable pageable);
}
