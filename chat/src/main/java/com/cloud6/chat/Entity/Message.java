package com.cloud6.chat.Entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Builder
@DynamoDbBean
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String messageId;
    private String createdAt;
    private String comment;
    private String type;
    private String createdBy;
    private String roomId;
    // 메시지가 어느 채팅방에 속하는지 나타내기 위함

    @DynamoDbPartitionKey
    @DynamoDbAttribute("messageId")
    public String getMessageId() {
        return messageId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("comment")
    public String getComment() {
        return comment;
    }

    @DynamoDbAttribute("type")
    public String getType() {
        return type;
    }

    @DynamoDbAttribute("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @DynamoDbAttribute("roomId")
    public String getRoomId() {
        return roomId;
    }
}
