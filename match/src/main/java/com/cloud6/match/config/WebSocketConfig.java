package com.cloud6.match.match.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cloud6.match.MatchHandler;
import com.cloud6.match.MatchQueueService;
import com.cloud6.match.TokenPublishProtocolService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MatchQueueService matchQueueService;
    private final TokenPublishProtocolService tokenPublishProtocolService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(matchHandler(), "/match");
    }

    @Bean
    public WebSocketHandler matchHandler() {
        return new MatchHandler(matchQueueService, tokenPublishProtocolService);
    }
}

