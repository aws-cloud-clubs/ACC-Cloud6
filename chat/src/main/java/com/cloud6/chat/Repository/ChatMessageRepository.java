package com.cloud6.chat.Repository;

import com.cloud6.chat.Entity.Message;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ChatMessageRepository {
    private final DynamoDbTable<Message> messageTable;

    public ChatMessageRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.messageTable = dynamoDbEnhancedClient.table("Message",
                TableSchema.fromBean(Message.class));
    }

    public List<Message> findAllByRoomId(String roomId) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(roomId)
                        .build()
        );
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional).build();

        return messageTable.query(queryEnhancedRequest).items().stream()
                .collect(Collectors.toList());
    }
}
