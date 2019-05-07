package com.example.redistest.redislua.service;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RateLimitServiceTest extends BaseJedisTest{

    @Test
    public void testScript1(){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            Object r = jedis.eval(
                    "return redis.pcall(\"get\", KEYS[1])",
                    Arrays.asList("name1"), Arrays.asList("zhonghaixiao"));
//            Object r = jedis.eval(
//                    "local sumKey = 0;\n" +
//                            "for i,v in ipairs(KEYS) do\n" +
//                            "    sumKey = sumKey + tonumber(v)\n" +
//                            "end\n" +
//                            "return sumKey",
//                    Arrays.asList("1","2","3"), Arrays.asList("4","5","6")
//            );
            System.out.println(r);
            return ;
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
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