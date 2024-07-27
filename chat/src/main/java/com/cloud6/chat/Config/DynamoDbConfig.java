package com.cloud6.chat.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Autowired
    private AwsProperties awsProperties;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsProperties.getAccessKeyId(), awsProperties.getSecretAccessKey())
                ))
                .endpointOverride(URI.create("https://dynamodb.ap-northeast-2.amazonaws.com"))
                .build();
    }
}
