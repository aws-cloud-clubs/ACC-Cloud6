package com.cloud6.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisMatchQueueService implements MatchQueueService {

    private final StringRedisTemplate template;

    @Override
    public void enqueueMatchEntry(String queueId, MatchEntry entry) {
        template.opsForList().leftPush(getWaitingQueueId(queueId), entry.toString());
    }

    @Override
    public void cancelMatchEntry(String queueId, MatchEntry entry) {
        template.opsForList().leftPush(getCanceledQueueId(queueId), entry.toString());
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
                log.info("deque: {}", waiting);
                if (template.opsForList().size(canceledQueueId) > 0) {
                    if (template.opsForList().index(canceledQueueId, -1).equals(waiting)) {
                        template.opsForList().rightPop(canceledQueueId);
                        log.info("skip: {}", waiting);

                        continue;
                    }
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
        return template.opsForList().size(getCanceledQueueId(queueId));
    }

    @Override
    public String getNewRoomId() {
        return String.valueOf(template.opsForValue().increment("room:seq"));
    }

    @Override
    public String getNewUserId() {
        return String.valueOf(template.opsForValue().increment("user:seq"));
    }
    
    private String getWaitingQueueId(String queueId) {
        return "waiting:" + queueId;
    }

    private String getCanceledQueueId(String queueId) {
        return "canceled:" + queueId;
    }
}

