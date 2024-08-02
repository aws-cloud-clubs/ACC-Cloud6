package com.cloud6.match.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisMatchQueueService implements MatchQueueService {

    private final StringRedisTemplate template;
    private final RedisConnectionFactory connectionFactory;

    @Override
    public void enqueueMatchEntry(String queueId, MatchEntry entry) {
        template.opsForList().leftPush(getWaitingQueueId(queueId), entry.toString());
    }

    @Override
    public void cancelMatchEntry(String queueId, MatchEntry entry) {
        template.opsForSet().add(getCanceledQueueId(queueId), entry.toString());
    }

    @Override
    public List<MatchEntry> dequeueMatchEntry(String queueId, int size) {
        List<MatchEntry> result = new ArrayList<>(size);
        String waitingQueueId = getWaitingQueueId(queueId);
        String canceledQueueId = getCanceledQueueId(queueId);

        log.info("try to dequeue {}", size);
        
        try {
            while (result.size() < size) {
                String waiting = template.opsForList().rightPop(waitingQueueId);
                log.info("dequeue: {}", waiting);
                if (template.opsForSet().isMember(canceledQueueId, waiting)) {
                    log.info("canceled: {}", waiting);
                    template.opsForSet().remove(canceledQueueId, waiting);
                    continue;
                }
                result.add(MatchEntry.of(waiting));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public long waitingSize(String queueId) {
        return waitingQueueSize(queueId) - canceledQueueSize(queueId);
    }

    @Override
    public long waitingQueueSize(String queueId) {
        return template.opsForList().size(getWaitingQueueId(queueId));
    }

    @Override
    public long canceledQueueSize(String queueId) {
        return template.opsForSet().size(getCanceledQueueId(queueId));
    }

    @Override
    public String getNewRoomId() {
        return String.valueOf(template.opsForValue().increment("room:seq"));
    }

    @Override
    public String getNewUserId() {
        return String.valueOf(template.opsForValue().increment("user:seq"));
    }

    @Override
    public void subscribeMatchResult(MatchResultListener listener, String queueId) {
        RedisConnection connection = connectionFactory.getConnection();
        String channelId = getChannelId(queueId);

        connection.subscribe((message, pattern) -> {
            MatchResult result = MatchResult.of(message.toString());
            listener.onMatchResult(result);
        }, channelId.getBytes());

    }

    @Override
    public void publishMatchResult(List<MatchEntry> entries, String roomId, String queueId) {
        RedisConnection connection = connectionFactory.getConnection();

        for (MatchEntry entry : entries) {
            MatchResult result = MatchResult.builder()
            .roomId(roomId)
            .userId(entry.getUserId())
            .nickname(entry.getNickname())
            .build();
            connection.publish(
                getChannelId(queueId).getBytes(), 
                result.toString().getBytes()
            );
        }
    }
    
    private String getWaitingQueueId(String queueId) {
        return "waiting:" + queueId;
    }

    private String getCanceledQueueId(String queueId) {
        return "canceled:" + queueId;
    }

    private String getChannelId(String queueId) {
        return "ch:" + queueId;
    }
}

