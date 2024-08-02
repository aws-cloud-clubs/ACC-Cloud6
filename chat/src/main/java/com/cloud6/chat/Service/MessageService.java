package com.cloud6.chat.Service;

import com.cloud6.chat.Entity.Message;
import com.cloud6.chat.Repository.ChatMessageRepository;
import com.cloud6.chat.Repository.MessageRepository;
import com.cloud6.chat.Utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatMessageRepository chatMessageRepository;

    public List<Message> getMessageByRoomId(String roomId)
    {
        return chatMessageRepository.findAllByRoomId(roomId);
    }
}
