package com.cloud6.match;

import com.cloud6.match.video.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedisRepository extends CrudRepository<Video, Long> {
    List<Video> findAllOrderByWaitingDesc();
}
