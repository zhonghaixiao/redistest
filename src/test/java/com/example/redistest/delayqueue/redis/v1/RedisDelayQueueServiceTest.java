package com.example.redistest.delayqueue.redis.v1;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

public class RedisDelayQueueServiceTest {

    //    @Autowired
    JedisPool jedisPool;

    RedisDelayQueueService service;

    @Before
    public void init(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMaxWaitMillis(10000);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 10000);
        service = new RedisDelayQueueService(jedisPool);
    }

    @Test
    public void addDelayTask() throws InterruptedException {

        for (int i = 0; i < 600; i++){
            Task task1 = Task.builder()
                    .id(UUID.randomUUID().toString())
                    .type("test-type")
                    .executeMillis(System.currentTimeMillis() +  (new Random().nextInt(8)) * 1000)
                    .build();

            service.addDelayTask(task1, task1.getExecuteMillis() - System.currentTimeMillis(), (run)->{});
        }

        Thread.sleep(100000);

    }
}