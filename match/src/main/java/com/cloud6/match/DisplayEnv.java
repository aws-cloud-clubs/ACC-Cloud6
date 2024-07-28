package com.cloud6.match;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("dev")
public class DisplayEnv {
    @Value("${cloud6.subscribe.id}")
    private String subscribeId;

    @PostConstruct
    private void displayEnv() {
        Map<String, String> envs = new HashMap<>();
        envs.put("CLOUD6_SUBSCRIBE_ID", subscribeId);
        // TODO: put other env variables here

        StringBuilder sb = new StringBuilder("\n ===== Environment Setup ===== \n");
        for (String key : envs.keySet()) {
            sb.append(" * " + key + "=" + envs.get(key));
        }
        sb.append('\n');
        log.info(sb.toString());
    }

}


