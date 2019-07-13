package com.example.guava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chenzhen
 * @date : 2019-07-13
 */

@RestController
public class GuavaController {

    private final static Logger logger = LoggerFactory.getLogger(GuavaController.class);

    @RequestMapping("/guava")
    @LxRateLimit(perSecond = 20)
    public String guava() throws InterruptedException {
        logger.info("guava");
        Thread.sleep(20);
        return "guava";
    }
}
