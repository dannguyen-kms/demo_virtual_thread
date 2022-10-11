package org.example;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class StructuredTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        var task1 = new Callable<String>(){
            public String call(){
                return "Update information";
            }
        };

        var task2 = new Callable<String>(){
            public String call() throws InterruptedException {
                Thread.sleep(2000);
                return "Send email";
            }
        };

        try(var scope = new StructuredTaskScope<String>()){
            Future<String> future1 = scope.fork(task1);
            Future<String> future2 = scope.fork(task2);
            scope.join();
            System.out.println(String.format("Done: %s + %s",future1.get(),future2.get()));
        }
        withExample1();
    }


    static Callable runnable =  new Callable<Integer>(){
        public Integer call() throws InterruptedException {
            Thread.sleep(2000);
            return 1;
        }
    };

    public static void withExample1() throws InterruptedException, TimeoutException {
        Instant start = Instant.now();
        try(var scope = new StructuredTaskScope<Integer>()){
            List<Future> list= new ArrayList<>();
                for(int i = 0; i < 10_000; i++) {
                    list.add(scope.fork(()->{
                        Thread.sleep(2000);
                        return 1;
                    }));
            }
            scope.joinUntil(Instant.ofEpochSecond(Instant.now().getEpochSecond()+4));

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Total elapsed time : " + timeElapsed);

            System.out.println( list.stream().mapToInt(i -> {
                try {
                    return (int) i.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).sum());
        }
    }


}
