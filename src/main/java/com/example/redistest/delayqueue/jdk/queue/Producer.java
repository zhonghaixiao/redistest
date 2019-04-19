package com.example.redistest.delayqueue.jdk.queue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    protected BlockingQueue queue;
    private int value;

    private static int id = 0;
    private final String name;

    public Producer(BlockingQueue queue, int value){
        this.queue = queue;
        this.value = value;
        this.name = "producer: [" + id++ + "]";
    }

    @Override
    public void run() {
        try{
            while (true){
                queue.put(name + " : " + value);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
