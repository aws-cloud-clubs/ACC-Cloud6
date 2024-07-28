package com.cloud6.match;

import java.util.List;

public interface MatchQueueService {
    void enqueueMatchEntry(String queueId, MatchEntry entry);
    void cancelMatchEntry(String queueId, MatchEntry entry);
    List<MatchEntry> dequeueMatchEntry(String queueId, int size);
    long waitingQueueSize(String queueId);
    long canceledQueueSize(String queueId);
    long waitingSize(String queueId);
    String getNewRoomId();
    String getNewUserId();
}

