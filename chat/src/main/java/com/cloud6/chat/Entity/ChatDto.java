package com.cloud6.chat.Entity;

import lombok.Data;

@Data
public class ChatDto {
    private String messageId;
    private String roomId;
    private String type;
    private String createdBy;
    private String comment;
    private String createdAt;

    public ChatDto(Message message) {
        this.messageId = message.getMessageId();
        this.roomId = message.getRoomId();
        this.type = message.getType();
        this.createdBy = message.getCreatedBy();
        this.comment = message.getComment();
        this.createdAt = message.getCreatedAt();
    }

    public String getMessage() {
        return this.comment;
    }
}
