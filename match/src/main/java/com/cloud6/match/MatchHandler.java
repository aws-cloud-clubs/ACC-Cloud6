package com.cloud6.match;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MatchHandler extends TextWebSocketHandler {

    private final MatchQueueService matchQueueService;

    @Value("${cloud6.subscribe.id}")
    private String subscribeId;

    @Value("${cloud6.match.size}")
    private int matchSize;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.getAttributes().containsKey("matched")) return;

        Map<String, String> query = parseQuery(session.getUri().getQuery());
        validateQuery(query);

        String queueId = query.get("video_id");
        String userId = session.getAttributes().get("user_id").toString();
        String nickname = query.get("nickname");

        matchQueueService.cancelMatchEntry(
            queueId,
            new MatchEntry(subscribeId, userId, nickname)
        );
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            Map<String, String> query = parseQuery(session.getUri().getQuery());
            validateQuery(query);

            String queueId = query.get("video_id");
            String nickname = query.get("nickname");
            String userId = matchQueueService.getNewUserId();

            session.getAttributes().put("user_id", userId);

            matchQueueService.enqueueMatchEntry(
                queueId,
                new MatchEntry(subscribeId, userId, nickname)
            );

            long waitingSize = matchQueueService.waitingSize(queueId);

            log.info("waiting for queue_id {} : {}", queueId, waitingSize);

            log.info("waiting for {} queue to fill...", queueId);
            matchQueueService.subscribeMatchResult((result) -> {
                if (result.getUserId().equals(userId)) {
                    // TODO: publish JWT ticket
                    log.info("publish ticket for user_id {}", userId);
                    try {
                        session.getAttributes().put("matched", true);
                        session.sendMessage(new TextMessage(result.toString()));
                        session.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }, subscribeId, queueId);

            if (waitingSize >= matchSize) {
                log.info("more than {} people is waiting. try matching...", matchSize);
                List<MatchEntry> entries = matchQueueService.dequeueMatchEntry(queueId, matchSize);
                log.info("successfully matched: {}", entries);

                String roomId = matchQueueService.getNewRoomId();
                matchQueueService.publishMatchResult(entries, roomId, queueId);
            } 

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error(exception.getMessage());
        exception.printStackTrace();
    }
    
    private Map<String, String> parseQuery(String src) {
        Map<String, String> result = new HashMap<>();

        if (src == null) return result;

        for (String entry : src.split("\\&")) {
            String[] split = entry.split("\\=");
            String key = split[0];
            String value = split.length == 2 ? split[1] : "";
            result.put(key, value);
        }

        return result;
    }

    private void validateQuery(Map<String, String> query) {
        List<String> required = List.of("video_id", "nickname");

        for (String key : required) {
            if (!query.containsKey(key))
                throw new RuntimeException("No '" + key + "' query provided to /match");
        }
    }

}

