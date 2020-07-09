package com.example.redistest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestRedis {

    @Autowired
    JedisPool jedisPool;

    @Test
    public void testRedis(){
        init();
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        test(jedis);

//        CountDownLatch latch = new CountDownLatch(1);
//        ExecutorService service = Executors.newCachedThreadPool();
//        init();
//        for (int i = 0; i < 1; i++){
//            service.submit(new Runnable() {
//
//                Jedis jedis = new Jedis("127.0.0.1", 6379);
//
//                @Override
//                public void run() {
//                    try {
//                        latch.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    test(jedis);
//
//                }
//            });
//        }
//
//        latch.countDown();
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    void init(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("count",  "20");
        System.out.println(jedis.get("count"));
        jedis.close();
    }

    void test(Jedis jedis){
        int count = Integer.valueOf(jedis.get("count"));
        jedis.watch("count");
        Transaction tran = jedis.multi();
        try{
            if (count > 0){
                tran.set("age", "jjj");
                tran.zadd("runoobkey", 111, "");
                tran.set("count", String.valueOf(count-1));
            }
            List<Object> res = tran.exec();
            System.out.println(res);

        }catch (Exception e){
            e.printStackTrace();
            tran.discard();
        }
        finally {
            jedis.close();
        }
    }

}
