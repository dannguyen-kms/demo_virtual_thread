package org.example;

public class CreateVirtualThread {
    public static void main(String[] args) throws InterruptedException {
        CreateVirtualThread createVirtualThread= new CreateVirtualThread();
        createVirtualThread.Builder();
    }

    Runnable runnable = () -> System.out.println("Inside Runnable");
    public void StartVirtualThread(){
        Thread.startVirtualThread(runnable);
        //or
        Thread.startVirtualThread(() -> {
            //Code to execute in virtual thread
            System.out.println("Inside Runnable");
        });
    }

    Runnable runnable2 = () -> {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Inside Runnable 2");};


    public void Builder() throws InterruptedException {
        Thread virtualThread = Thread.ofVirtual().start(runnable);
        //or
        Thread.Builder builder = Thread.ofVirtual().name("JVM-Thread");
        Thread t1 = builder.start(runnable);
        Thread t2 = builder.start(runnable2);
        Thread.sleep(3000);
        //t2.join();
        System.out.println("end");
    }


}
