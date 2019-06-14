package com.example.redistest.concurrent;

import com.sun.jmx.remote.internal.ArrayQueue;

import javax.xml.ws.Service;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ConcurrentTest {

    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args){

        int[] data = new int[]{1,2,3,4,5,6,7,8};
        int[] newData = Arrays.copyOf(data, 100);
        System.arraycopy(newData, 0, newData, 1, 1);
        for (int i = 0; i < newData.length; i++){
            System.out.println(newData[i]);
        }

    }

}
