package com.example.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.anyInt;

/**
 * @author : chenzhen
 * @date : 2019-07-13 20:43
 */
public class TestBulkhead {
    public static void main(String[] args) {

    }
}
