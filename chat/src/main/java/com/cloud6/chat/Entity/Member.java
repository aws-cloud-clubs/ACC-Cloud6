package com.cloud6.chat.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;
import org.springframework.data.annotation.Id;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Builder
@DynamoDbBean
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class Member {
    private String memberId;
    private String memberName;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("memberId")
    public String getMemberId() {
        return memberId;
    }

    @DynamoDbAttribute("memberName")
    public String getMemberName() {
        return memberName;
    }
}
