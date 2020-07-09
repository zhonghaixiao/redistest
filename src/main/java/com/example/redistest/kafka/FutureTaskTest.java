package com.example.redistest.kafka;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static int sizeOfVarint(int value) {
        int v = (value << 1) ^ (value >> 31);
        int bytes = 1;
        while ((v & 0xffffff80) != 0L) {
            bytes += 1;
            v >>>= 7;
        }
        return bytes;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println(1>>31);
        System.out.println(sizeOfVarint(1));
        System.out.println(sizeOfVarint(100));
        System.out.println(sizeOfVarint(1000));
        System.out.println(sizeOfVarint(100000));
        System.out.println(sizeOfVarint(10000000));
        System.out.println(sizeOfVarint(1000000000));

//        FutureTask<Integer> task = new FutureTask<>(() -> {
//            int sum = 0;
//            for (int i = 0; i < 1000; i++){
//                Thread.sleep(1);
//                sum += i;
//            }
//            return sum;
//        });
//        task.run();
//        System.out.println(task.get());
    }


}
