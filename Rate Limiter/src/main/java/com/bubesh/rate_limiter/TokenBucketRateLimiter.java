package com.bubesh.rate_limiter;

import java.util.concurrent.locks.Lock;

public class TokenBucketRateLimiter implements RateLimiter {

    private final long capacity;
    private final long refillWindowDurationInSeconds ;
    private long availableTokens;
    private long previousRefillTimeStamp;

    public TokenBucketRateLimiter(long capacity, long refillWindowDurationInSeconds) {
        this.capacity = capacity;
        this.availableTokens = capacity;
        this.refillWindowDurationInSeconds = refillWindowDurationInSeconds;
        this.previousRefillTimeStamp = System.currentTimeMillis();
    }

    @Override
    public boolean tryConsume() {
        synchronized (this) {
            refill();
            if(availableTokens > 0) {
                availableTokens--;
                return true;
            }
            return false;
        }
    }

    private void refill() {
        long currentTimeStamp = System.currentTimeMillis();
        if((currentTimeStamp - previousRefillTimeStamp) >= (refillWindowDurationInSeconds*1000)) {
            availableTokens = capacity;
        }
        previousRefillTimeStamp = currentTimeStamp;
    }
}
