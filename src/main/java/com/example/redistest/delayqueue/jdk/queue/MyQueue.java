package com.example.redistest.delayqueue.jdk.queue;

import java.util.AbstractQueue;
import java.util.Iterator;

public class MyQueue<T> extends AbstractQueue<T> {

    private static final int DEFAULT_SIZE = 10;

    final Object[] items;

    int putIndex;

    int takeIndex;

    public MyQueue(){
        this(DEFAULT_SIZE);
    }

    public MyQueue(int size){
        items = new Object[size];
        putIndex = 0;
        takeIndex = -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = takeIndex;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                T t = (T) items[index];
                index = (index + 1) % items.length;
                return t;
            }
        };
    }

    @Override
    public int size() {
        return items.length;
    }

    @Override
    public boolean offer(T t) {
        if ((putIndex + 1) % items.length != takeIndex){

        }
        items[putIndex] = t;
        return false;
//        if (count == items.length){
//            return false;
//        }else{
//            items[putIndex] = t;
//            putIndex = (putIndex + 1) % items.length;
//            count++;
//            return true;
//        }
    }

    @Override
    public T poll() {
        return null;
//        if (count == 0){
//            return null;
//        }else{
//            T t = (T) items[takeIndex];
//            takeIndex = (takeIndex + 1) % items.length;
//            count--;
//            return t;
//        }
    }

    @Override
    public T peek() {
        return (T) items[takeIndex];
    }
}
