package com.cloud6.match.match;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTokenPublishProtocolService implements TokenPublishProtocolService {

    private final MatchQueueService matchQueueService;

    @Value("${cloud6.jwt.secret}")
    private String secret;

    @Override
    public boolean publishToken(WebSocketSession session, MatchResult result) {
        if (!session.isOpen()) return false;

        try {
            session.getAttributes().put("matched", true);
            session.sendMessage(new TextMessage("SUCCESS"));
            ObjectMapper objectMapper = new ObjectMapper();
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(result)));
            session.close();

            return true;
        } catch (JWTCreationException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean sendWaitingCount(WebSocketSession session, String queueId) {
        if (!session.isOpen()) return false;

        try {
            long waitingCount = matchQueueService.waitingSize(queueId);
            session.sendMessage(new TextMessage("WAITING"));
            session.sendMessage(new TextMessage(String.valueOf(waitingCount)));
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean sendError(WebSocketSession session) {
        if (!session.isOpen()) return false;

        try {
            session.sendMessage(new TextMessage("ERROR"));
            session.close();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean sendTimeout(WebSocketSession session) {
        if (!session.isOpen()) return false;

        try {
            session.sendMessage(new TextMessage("TIMEOUT"));
            session.close();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    
}

