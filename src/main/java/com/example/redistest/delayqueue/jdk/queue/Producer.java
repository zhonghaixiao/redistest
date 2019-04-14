package com.example.redistest.delayqueue.jdk.queue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    protected BlockingQueue queue;
    public Producer(BlockingQueue queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            queue.put(1);
            Thread.sleep(1000);
            queue.put(1);
            Thread.sleep(1000);
            queue.put(1);
            Thread.sleep(1000);
            queue.put(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
