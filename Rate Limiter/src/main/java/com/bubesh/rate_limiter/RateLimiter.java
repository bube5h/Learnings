package com.bubesh.rate_limiter;

import lombok.AllArgsConstructor;

public interface RateLimiter {

    boolean tryConsume();

}
