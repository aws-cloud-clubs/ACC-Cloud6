package com.cloud6.chat.Entity;

import lombok.Data;

@Data
public class ChatDto {
//    private String messageId;
    private String memberId;
    private String roomId;
    private String type;
    private String createdBy;
    private String comment;
    private String createdAt;

    public String getMessage() {
        return this.comment;
    }
}
