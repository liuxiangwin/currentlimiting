package com.example.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author : chenzhen
 * @date : 2019-07-13 19:34
 */

@Configuration
public class CurrentLimiterConfig {

    protected final RateLimiter rateLimiter;
    protected final RateLimiter rateLimiter2;
    protected final Bulkhead bulkhead;
    protected final ThreadPoolBulkhead threadPoolBulkhead;

    public CurrentLimiterConfig(){
        rateLimiter = RateLimiter.of("Limiter1",RateLimiterConfig.custom()
                .limitForPeriod(20)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(5))
                .build());
        rateLimiter2 = RateLimiter.of("Limiter2",RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(5))
                .timeoutDuration(Duration.ofMillis(5))
                .build());
        bulkhead = Bulkhead.of("Bulkhead", BulkheadConfig.custom()
                .maxConcurrentCalls(1)
                .build());
        threadPoolBulkhead = ThreadPoolBulkhead.of("ThreadPoolBulkhead", ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(10)
                .coreThreadPoolSize(2)
                .queueCapacity(20)
                .build());
    }
}
