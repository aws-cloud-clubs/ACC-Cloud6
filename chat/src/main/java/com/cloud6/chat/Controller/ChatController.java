package com.cloud6.chat.Controller;

import com.cloud6.chat.Config.JwtTokenProvider;
import com.cloud6.chat.Entity.ChatDto;
import com.cloud6.chat.Entity.ChatMemberDto;
import com.cloud6.chat.Entity.Message;
import com.cloud6.chat.Repository.ChatMessageRepository;
import com.cloud6.chat.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SimpMessagingTemplate messagingTemplate;
    // private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/{roomId}/addMember")
    @SendTo("/topic/{roomId}")
    public ChatMemberDto addMemberToChatRoom(@DestinationVariable String roomId,
                                             @Header("token") String token) {
        return chatService.createChatRoom(token);
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatDto sendMessage(@DestinationVariable String roomId, @Payload Message message,
                               @Header("token") String token) {
        String loginId = jwtTokenProvider.getNicknameFromToken(token);
        return chatService.createChat(roomId, message.getComment(), loginId);
    }
}
