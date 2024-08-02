package com.cloud6.match.match;

import org.springframework.web.socket.WebSocketSession;

public interface TokenPublishProtocolService {
    boolean publishToken(WebSocketSession session, MatchResult result);
    boolean sendWaitingCount(WebSocketSession session, String queueId);
    boolean sendError(WebSocketSession session);
    boolean sendTimeout(WebSocketSession session);
}

