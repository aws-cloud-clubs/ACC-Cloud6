package com.cloud6.match;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class MatchResult {
    private final String roomId;
    private final String userId;
    private final String nickname;

    @Override
    public String toString() {
        return roomId + ":" + userId + ":" + nickname;
    }

    public static MatchResult of(String src) {
        if (src == null) throw new RuntimeException("MatchResult src is null");

        String[] split = src.split("\\:");
        if (split.length != 3) throw new RuntimeException("Invalid MatchResult src: " + src);

        return new MatchResult(split[0], split[1], split[2]);
    }
}

