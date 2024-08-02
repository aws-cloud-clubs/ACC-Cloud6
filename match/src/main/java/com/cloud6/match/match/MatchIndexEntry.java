package com.cloud6.match.match;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class MatchIndexEntry {
    private final String video_id;
    private final long waiting;

    @Override
    public String toString() {
        return waiting + ":" + video_id;
    }

    public static MatchIndexEntry of(String src) {
        if (src == null) throw new RuntimeException("MatchIndexEntry src is null");

        String[] split = src.split("\\:");
        if (split.length != 2) throw new RuntimeException("Invalid MatchIndexEntry src: " + src);

        return new MatchIndexEntry(split[1], Long.parseLong(split[0]));
    }
}

