package com.cloud6.chat.Repository;

import com.cloud6.chat.Entity.ChatRoom;
import com.cloud6.chat.Entity.Message;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class MessageRepository {
    private final DynamoDbTable<Message> messageTable;

    public MessageRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.messageTable = dynamoDbEnhancedClient.table("DYNAMO_DB_SAMPLE",
                TableSchema.fromBean(Message.class));
    }
    public Message save(Message message) {
        messageTable.putItem(message);
        return message;
    }
}
