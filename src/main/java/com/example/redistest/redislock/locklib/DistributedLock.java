package com.example.redistest.redislock.locklib;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 基于redis实现分布式锁
 */
public class DistributedLock {

    private Jedis jedis;

    private static final String LOCK_PREFIX = "lock:";

    public DistributedLock(Jedis jedis){
        this.jedis = jedis;
    }

    /**
     * 获取锁
     * @param lockName
     * @param acquireTimeout 获取锁的超时时间
     * @param timeout 锁的超时时间
     * @return
     */
    public String tryLock(String lockName, long acquireTimeout, long timeout) throws JedisException {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        //获取锁的超时时间
        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end){
            /**version1*/
            if (jedis.setnx(lockKey, identifier) == 1){
                //设置成功后设置超时时间
                jedis.pexpire(lockKey, timeout);
                return identifier;
            }

            //设置失败，锁已被占用，如果锁没有设置超时时间，设置锁的超时时间
            if (jedis.ttl(lockKey) == -1){
                jedis.pexpire(lockKey, timeout);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean releaseLock(String lockName, String identifier){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(LOCK_PREFIX + lockName), Collections.singletonList(identifier));
        return (long)(result) == 1;
//        String lockKey = "lock:" + lockName;
//        while (true){
//            //监视lockKey
//            jedis.watch(lockKey);
//            if (StringUtils.equals(identifier, jedis.get(lockKey))){
//                Transaction transaction = jedis.multi();
//                transaction.del(lockKey);
//                List<Object> results = transaction.exec();
//                if (results == null){
//                    continue;
//                }
//                jedis.unwatch();
//                return true;
//            }
//        }
    }

    public boolean tryLock2(String key, String request){
        String result = jedis.set(LOCK_PREFIX + key, request, "NX", "PX", 1000);
        return StringUtils.equals("1", result);
    }

    /**
     * 阻塞锁
     */
    public void lock(String key, String request) throws InterruptedException{
        for (;;){
            String result = jedis.set( LOCK_PREFIX + key, request, "NX", "PX", 1000);
            if (StringUtils.equals(result, "1")){
                break;
            }
            //防止一直消耗cpu
            Thread.sleep(20);
        }
    }

    /**
     * 自定义阻塞时间
     * @param key
     * @param request
     * @param blockTime
     * @return
     * @throws InterruptedException
     */
    public boolean lock(String key, String request, int blockTime) throws InterruptedException{
        while (blockTime >= 0){
            String result = jedis.set(LOCK_PREFIX + key, request, "NX", "PX", 1000);
            System.out.println(result);
            if (StringUtils.equals(result, "1")){
                return true;
            }
            blockTime -= 20;
            Thread.sleep(20);
        }
        return false;
    }

    public boolean unlock(String key, String request){
        //lua script
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(LOCK_PREFIX + key), Collections.singletonList(request));
        return (long)(result) == 1;
    }

}










