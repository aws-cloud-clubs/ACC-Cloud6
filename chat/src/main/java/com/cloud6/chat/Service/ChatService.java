package com.cloud6.chat.Service;

import com.cloud6.chat.Entity.*;
import com.cloud6.chat.Repository.ChatMessageRepository;
import com.cloud6.chat.Repository.ChatRoomRepository;
import com.cloud6.chat.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void createChat(String roomId, ChatDto chatDto) {
        Message newMessage = Message.builder()
                .messageId(UUID.randomUUID().toString())
                .memberId(chatDto.getMemberId())
                .roomId(roomId)
                .type("MESSAGE")
                .createdBy(chatDto.getCreatedBy())
                .comment(chatDto.getComment())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        messageRepository.save(newMessage);

        // simpMessagingTemplate.convertAndSend("/topic/" + roomId, newMessage);
    }
    /*
    public Message sendMessage(Message message) {
        message.setMessageId(UUID.randomUUID().toString());
        message.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return messageRepository.save(message);
    }
    */
}
