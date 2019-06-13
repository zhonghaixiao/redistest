package com.example.redistest.delayqueue.jdk.queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueExample {


    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        Producer producer1 = new Producer(queue, 1);
        Producer producer2 = new Producer(queue, 2);
        Producer producer3 = new Producer(queue, 3);
        Consumer consumer = new Consumer(queue);
        new Thread(producer1).start();
        new Thread(producer2).start();
        new Thread(producer3).start();
        new Thread(consumer).start();
        Thread.sleep(4000);
    }

}
