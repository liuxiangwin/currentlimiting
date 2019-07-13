package com.example.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.CompletionStage;

/**
 * @author : chenzhen
 * @date : 2019-07-13 18:28
 */
@RestController
public class RateLimiterController extends CurrentLimiterConfig {

    private final static Logger logger = LoggerFactory.getLogger(RateLimiterController.class);

    private final RateLimiterService service = new RateLimiterService();

    @RequestMapping("/resilience")
    public String resilience() {
        String result = Try.ofSupplier(RateLimiter.decorateSupplier(rateLimiter, service::service))
                .recover(CommonException.class, "fail")
                .recover(RequestNotPermitted.class,"Request not permitted for limiter")
                .get();
        logger.info(result);
        return result;
    }

    @RequestMapping("/resilience2")
    public String resilience2() {
        logger.info(String.valueOf(System.currentTimeMillis()));
        String result = Try.ofSupplier(Bulkhead.decorateSupplier(bulkhead, service::service2))
                .recover(CommonException.class, "fail")
                .recover(RequestNotPermitted.class,"Request not permitted for limiter")
                .recover(BulkheadFullException.class,"Bulkhead name is full")
                .get();
        logger.info(result);
        return result;
    }



}
