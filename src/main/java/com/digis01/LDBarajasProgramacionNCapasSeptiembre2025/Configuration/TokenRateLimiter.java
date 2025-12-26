package com.digis01.LDBarajasProgramacionNCapasSeptiembre2025.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class TokenRateLimiter {

    private final Map<String, TokenUsage> tokenUsageMap = new ConcurrentHashMap<>();

    private final int MAX_REQUESTS = 5 ;
    private final long WINDOW_MS = 900_000;

    public boolean isAllowed(String token) {
        long now = System.currentTimeMillis();

        tokenUsageMap.putIfAbsent(token, new TokenUsage(0, now));
        TokenUsage usage = tokenUsageMap.get(token);

        synchronized (usage) {
            if (now - usage.windowStart > WINDOW_MS) {
                usage.windowStart = now;
                usage.requestCount = 0;
            }
            usage.requestCount++;
            return usage.requestCount <= MAX_REQUESTS;
        }
    }

    private static class TokenUsage {

        int requestCount;
        long windowStart;

        public TokenUsage(int requestCount, long windowStart) {
            this.requestCount = requestCount;
            this.windowStart = windowStart;
        }
    }
}
