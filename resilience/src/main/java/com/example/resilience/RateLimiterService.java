package com.example.resilience;

import org.springframework.stereotype.Service;

/**
 * @author : chenzhen
 * @date : 2019-07-13 18:29
 */
@Service
public class RateLimiterService {

    public String service() {
        return "resilience";
    }

    public String service2() {
        return "resilience2";
    }
}
