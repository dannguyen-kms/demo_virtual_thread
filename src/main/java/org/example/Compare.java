package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Compare {
    public static void main(String[] args) {
        Compare compare = new Compare();
//        compare.runOldThread();
        compare.runVirtualThread();
    }
    final AtomicInteger atomicInteger = new AtomicInteger();
    Runnable runnable = () -> {
        try {
            Thread.sleep(Duration.ofSeconds(10));
        } catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("Work Done - " + atomicInteger.incrementAndGet());
    };
    Instant start = Instant.now();
    public void runOldThread(){
        try (var executor = Executors.newFixedThreadPool(20_000)) {
            for(int i = 0; i < 20_000; i++) {
                executor.submit(runnable);
            }
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Total elapsed time : " + timeElapsed);
    }

    public void runVirtualThread(){
        Instant start = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for(int i = 0; i < 20_000; i++) {
                executor.submit(runnable);
            }
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Total elapsed time : " + timeElapsed);
    }

}
