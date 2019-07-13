package com.example.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chenzhen
 * @date : 2019-07-13
 */

@RestController
public class HystrixController {

    private final static Logger logger = LoggerFactory.getLogger(HystrixController.class);

    @RequestMapping("/hystrix1")
    @HystrixCommand(
            fallbackMethod = "error",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),//10个核心线程
                    @HystrixProperty(name = "maxQueueSize", value = "100"),//最大线程数100
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "10")},//队列阈值10
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"), //命令执行超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1000"), //若干10s一个窗口内失败1000次, 则达到触发熔断的最少请求量
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1") //断路1s后尝试执行, 默认为5s
            })
    public String hystrix() throws InterruptedException {
        Thread.sleep(500);
        logger.info("hystrix1");
        return "hystrix1";
    }

    @RequestMapping("/hystrix2")
    @HystrixCommand(
            fallbackMethod = "error",

            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "20"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public String hystrix2() throws InterruptedException {
        Thread.sleep(500);
        logger.info("hystrix2");
        return "hystrix2";
    }

    public String error() {
        logger.info("fail");
        return "fail";
    }
}
