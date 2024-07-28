package com.cloud6.match;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class MatchEntry {
    private final String publisherId;
    private final String userId;
    private final String nickname;

    @Override
    public String toString() {
        return publisherId + ":" + userId + ":" + nickname;
    }

    public static MatchEntry of(String src) {
        if (src == null) throw new RuntimeException("MatchEntry src is null");

        String[] split = src.split("\\:");
        if (split.length != 3) throw new RuntimeException("Invalid MatchEntry src: " + src);

        return new MatchEntry(split[0], split[1], split[2]);
    }
}

