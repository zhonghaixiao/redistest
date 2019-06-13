package com.example.redistest.redislua.service;

import org.junit.Before;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class BaseJedisTest {

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


}
