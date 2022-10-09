package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Compare {
    public static void main(String[] args) throws InterruptedException {
        Compare compare = new Compare();
//        compare.runOldThread();
        compare.runVirtualThread();
    }

    public void runOldThread() throws InterruptedException {
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
    }

    public void runVirtualThread(){
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
        for(var thread:threads){
            System.out.println(i++);
            thread.start();
        }
    }

}
