package com.example.redistest.testvm;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {

    static class OOMObject{
        private byte[] placeHolder = new byte[64*1024];
    }

//    BeanFactoryPostProcessor

    public static void main(String[] args) throws InterruptedException {
//        AutowireCapableBeanFactory
        OOMObject a = new OOMObject();
        OOMObject b = new OOMObject();
        new Thread(()->{
            synchronized (a){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    while (true){
                        System.out.println("a");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        new Thread(()->{
            synchronized (b){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    while (true){
                        System.out.println("b");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        Thread.sleep(1000000);
    }

}
