package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Compare {
    public static void main(String[] args) throws InterruptedException {
        Compare compare = new Compare();
        compare.newVirtualThreadPerTaskExecutor();

    }

    public void runOldThread() throws InterruptedException {
        Instant start = Instant.now();
        var threads = IntStream.range(0,100_000).mapToObj(
                i -> new Thread(()->{
                    try {
                        Thread.sleep(5_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
        ).toList();
        var i =0;
        for(var thread:threads){
            System.out.println(i++);
            thread.start();
        }
        for(var thread:threads){
            thread.join();
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Total elapsed time : " + timeElapsed);
    }

    public void runVirtualThread() throws InterruptedException {
        var threads = IntStream.range(0,100_000).mapToObj(
                i -> Thread.ofVirtual().unstarted(()->{
                    try {
                        Thread.sleep(5_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
        ).toList();
        var i =0;
        Instant start = Instant.now();
        for(var thread:threads){
            System.out.println(i++);
            thread.start();
        }
        for(var thread:threads){
            thread.join();
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Total elapsed time : " + timeElapsed);
    }
    public void newThreadPerTaskExecutor(){
        Instant start = Instant.now();
        try (var executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
            IntStream.range(0, 100_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println(i);
                return i;
            }));
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Total elapsed time : " + timeElapsed);
    }

    public void newVirtualThreadPerTaskExecutor(){
        Instant start = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println(i);
                return i;
            }));
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Total elapsed time : " + timeElapsed);
    }

}
