package com.example.redistest.redislua.service;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

import static org.junit.Assert.*;

public class RateLimitServiceTest {

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

    @Test
    public void accessLimit() {
        int i;
        for (i = 0; i < 20; i++){
            if (accessOnce()){
                System.out.println("access times: [" + i + "]");
            }else{
                break;
            }
        }
        assertTrue(i == 10);
    }

    private boolean  accessOnce(){
        Jedis jedis = null;
        RateLimitService service = new RateLimitService();
        try{
            jedis = jedisPool.getResource();
            return service.accessLimit("127.0.0.1", 10, 1000, jedis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return false;
    }

}