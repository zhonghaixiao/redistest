package com.example.redistest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    JedisPool jedisPool;

    @Test
    public void testRedis(){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set("name", "haixiao");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }

    }

}
