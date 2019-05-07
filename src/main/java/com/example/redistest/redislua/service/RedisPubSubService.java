package com.example.redistest.redislua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

@Service
public class RedisPubSubService {

    @Autowired
    private JedisPool jedisPool;

    @PostConstruct
    public void execute(){
        new Thread(new SubRunnable()).start();
        new Thread(new PubRunnable()).start();
    }

    class SubRunnable implements Runnable{

        @Override
        public void run() {
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                jedis.subscribe(new Subscriber(), "talk");
            }finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        }
    }

    class PubRunnable implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++){
                Jedis jedis = null;
                try{
                    jedis = jedisPool.getResource();
                    jedis.publish("talk", "hello" + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (jedis != null){
                        jedis.close();
                    }
                }
            }
        }
    }

}
