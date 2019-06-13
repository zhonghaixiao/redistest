package com.example.redistest.delayqueue.jdk.queue;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class MyQueueTest {

    @Test
    public void iterator() {

        MyQueue<String> myQueue = new MyQueue<>();
        for (int i = 0; i < 10; i++){
            myQueue.add(String.valueOf(i));
        }
        Iterator<String> itr = myQueue.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }

    }
}