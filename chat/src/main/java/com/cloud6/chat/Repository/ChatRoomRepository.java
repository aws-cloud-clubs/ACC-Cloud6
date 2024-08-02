package com.cloud6.chat.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.cloud6.chat.Entity.ChatRoom;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Repository
// @EnableScan
public class ChatRoomRepository {
    private final DynamoDbTable<ChatRoom> chatRoomTable;

    public ChatRoomRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.chatRoomTable = dynamoDbEnhancedClient.table("ChatRoom",
                TableSchema.fromBean(ChatRoom.class));
    }


    public void save(ChatRoom chatRoom) {
        chatRoomTable.putItem(chatRoom);
    }

    public ChatRoom findById(String id) {
        QueryConditional conditional = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(id)
                        .build()
            );
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(conditional)
                .limit(1)
                .build();
        return chatRoomTable.query(queryRequest).items().stream()
                .findAny()
                .orElseGet(()->null);
    }
}
