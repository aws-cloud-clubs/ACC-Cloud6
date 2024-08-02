package com.cloud6.chat.Controller;

import com.cloud6.chat.Entity.ChatDto;
import com.cloud6.chat.Entity.ChatMemberDto;
import com.cloud6.chat.Entity.Message;
import com.cloud6.chat.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatDto sendMessage(@DestinationVariable String roomId,
                               @Payload ChatDto chatDto) {
        chatService.createChat(roomId, chatDto);
        return chatDto;
    }
}
