package com.cloud6.chat.Service;

import com.cloud6.chat.Config.JwtTokenProvider;
import com.cloud6.chat.Entity.ChatDto;
import com.cloud6.chat.Entity.ChatMemberDto;
import com.cloud6.chat.Entity.ChatRoom;
import com.cloud6.chat.Entity.Message;
import com.cloud6.chat.Repository.ChatMessageRepository;
import com.cloud6.chat.Repository.ChatRoomRepository;
import com.cloud6.chat.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMemberDto createChatRoom(String token) {
        String roomId = jwtTokenProvider.getRoomIdFromToken(token);
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        String nickname = jwtTokenProvider.getNicknameFromToken(token);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId);
        if (chatRoom == null) {
            throw new RuntimeException("Chat room not found");
        }
        chatRoom.addMember(nickname);   // 멤버로 닉네임 추가..??
        // 멤버인지 userId인지 생각해보기

        chatRoomRepository.save(chatRoom);

        return new ChatMemberDto(roomId, nickname);
    }

    public ChatDto createChat(String roomId, String message, String loginId) {
        Message newMessage = Message.builder()
                .messageId(UUID.randomUUID().toString())
                .roomId(roomId)
                .type("MESSAGE")
                .createdBy(loginId)
                .comment(message)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        messageRepository.save(newMessage);

        return new ChatDto(newMessage);
    }
    /*
    public Message sendMessage(Message message) {
        message.setMessageId(UUID.randomUUID().toString());
        message.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return messageRepository.save(message);
    }
    */
}
