package com.cloud6.chat.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;
import org.springframework.data.annotation.Id;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Set;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ChatRoom {

    private String roomId;
    private String roomName;
    private Set<String> memberList;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("roomId")
    public String getRoomId() {
        return roomId;
    }

    @DynamoDbAttribute("roomName")
    public String getRoomName() {
        return roomName;
    }

    @DynamoDbAttribute("memberList")
    public String getMemberList() {
        return String.join(",", memberList);
    }


    public void addMember(String member) {
        this.memberList.add(member);
    }
}
