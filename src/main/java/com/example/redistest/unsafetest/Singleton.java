package com.example.redistest.unsafetest;

public class Singleton {
    private static volatile Singleton singleton;
    public static Singleton getInstance(){
        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    //防止指令重排
                    singleton = new Singleton();
                }
            }
        }
        return null;
    }
}
