package com.example.redistest.kafka;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Metadata {

    public static void main(String[] args){
        Metadata metadata = new Metadata();
        CountDownLatch latch = new CountDownLatch(10);
        Runnable runnable = ()->{
            latch.countDown();
            try {
                String result = metadata.getValue("");
                System.out.println(Thread.currentThread().getName() + " : " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 10; i++){
            new Thread(runnable).start();
        }
        new Thread(()->{
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(metadata.getValue(""));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    ReentrantLock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    boolean inProgress = false;

    long lastStamp = 0;

    String curValue;

    public String getValue(String key) throws InterruptedException {
        lock.lock();
        try{
            while (inProgress){
                condition.await();
            }
            long currentStamp = System.currentTimeMillis();
            if (currentStamp - lastStamp > 5000){
                inProgress = true;
                String value = getFromRemote("");
                curValue = value;
                lastStamp = System.currentTimeMillis();
                inProgress = false;
                condition.signalAll();
                return value;
            }else{
                return curValue;
            }
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            lock.unlock();
        }
    }

    private String getFromRemote(String key) throws InterruptedException{
        Thread.sleep(1000);
        return "user : " + System.currentTimeMillis();
    }



}
