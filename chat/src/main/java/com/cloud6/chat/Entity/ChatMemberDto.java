package com.cloud6.chat.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMemberDto {
    private String roomId;
    private String nickname;
}
