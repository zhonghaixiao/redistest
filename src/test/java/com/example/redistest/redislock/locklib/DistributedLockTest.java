package com.example.redistest.redislock.locklib;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.swing.*;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

import static org.junit.Assert.*;

//@RunWith()
public class DistributedLockTest {

//    @Autowired
    JedisPool jedisPool;

    @Before
    public void init(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMaxWaitMillis(10000);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 10000);
    }

    ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void testTryLock() {

        String count = "countInTestTryLock";

        setInitValue(count, "500");

        Runnable runnable = () -> {
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                DistributedLock lock = new DistributedLock(jedis);
                String identifier = lock.tryLock(count, 5000, 1000);
                System.out.println("Thread [ " + Thread.currentThread().getName() + "] get the lock  ==");
                jedis.incrBy(count, -1);
                System.out.println("count = " + jedis.get(count));
                if (lock.releaseLock(count, identifier)){
                    System.out.println("Thread [ " + Thread.currentThread().getName() + "] release the lock");
                }
            }finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        };

        for (int i = 0; i < 500; i++){
            executorService.submit(runnable);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue("The count = 0 ", StringUtils.equals("0", getValue(count)));

    }

    @Test
    public void testLock() {

        String count = "countInTestLock";

        setInitValue(count, "500");

        Runnable runnable = () -> {
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                DistributedLock lock = new DistributedLock(jedis);
                String request = UUID.randomUUID().toString();
                boolean isLocked = lock.lock(count, request, 2000);
                if (isLocked){
                    System.out.println("Thread [ " + Thread.currentThread().getName() + "] get the lock  ==");
                    jedis.incrBy(count, -1);
                    System.out.println("count = " + jedis.get(count));
                    boolean isUnlocked  =lock.unlock(count, request);
                    if (isUnlocked){
                        System.out.println("Thread [ " + Thread.currentThread().getName() + "] release the lock");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        };

        for (int i = 0; i < 500; i++){
            executorService.submit(runnable);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue("The count = 0 ", StringUtils.equals("0", getValue(count)));

    }

    @Test
    public void testExecScript(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set("lock:name", "zhonghaixiao");
            DistributedLock lock = new DistributedLock(jedis);
            lock.unlock("name", "zhong");
            lock.unlock("name", "zhonghaixiao");
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

    }

    private void setInitValue(String count, String initValue) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(count, initValue);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    private String getValue(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String v = jedis.get(key);
            if (v != null){
                return v;
            }
            return null;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }



}