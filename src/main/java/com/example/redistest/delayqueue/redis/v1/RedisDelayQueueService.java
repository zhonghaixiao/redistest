package com.example.redistest.delayqueue.redis.v1;

import com.alibaba.fastjson.JSON;
import com.example.redistest.redislock.locklib.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class RedisDelayQueueService {

    private JedisPool jedisPool;

    private ExecutorService executorService;

    private HashSet<String> existWorkers;

    private static final String QUEUE_NAME = "delayed-queue";

    public RedisDelayQueueService(JedisPool jedisPool){
        this.jedisPool = jedisPool;
        executorService = Executors.newCachedThreadPool();
        existWorkers = new HashSet<>();
        startPool();
    }

    private void startPool() {

        executorService.submit(new PoolRunnable(jedisPool));

    }

    /**
     * 插入延时队列（zset）
     * @param task
     * @param delayInMilli 延时执行的毫秒数
     */
    public Boolean addDelayTask(Task task, long delayInMilli){
        Jedis jedis = null;
        try {
            if (!existWorkers.contains(task.getType())){
                existWorkers.add(task.getType());
                executorService.submit(new WorkRunnable(jedisPool, task.getType()));
            }
            jedis = jedisPool.getResource();
            long r = jedis.zadd(QUEUE_NAME, delayInMilli + System.currentTimeMillis(), JSON.toJSONString(task));
            return r == 1;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 批量插入延时任务
     * @param task
     * @param delayList
     * @return
     */
    public Boolean addDelayTask(List<String> task, List<Long> delayList){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < task.size(); i++){
                pipeline.zadd(QUEUE_NAME, delayList.get(i) + System.currentTimeMillis(), task.get(i));
            }
            Response<List<Object>> r = pipeline.exec();
            return r.get() != null;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    private class PoolRunnable implements Runnable{

        private JedisPool pool;

        private static final long DEFAULT_DELAY = 10;

        public PoolRunnable(JedisPool jedisPool){
            this.pool = jedisPool;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " PoolRunnable begin :");
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                //开始循环遍历zset
                while (true){
                    if (Thread.currentThread().isInterrupted()){
                        break;
                    }
                    Set<Tuple> r = jedis.zrangeWithScores(QUEUE_NAME, 0 , 1);
                    if (r == null || r.size() < 1){
                        Thread.sleep(DEFAULT_DELAY);
                    }else{
                        System.out.println(Thread.currentThread().getName() + " PoolRunnable find task");
                        Tuple tuple = r.iterator().next();
                        double score = tuple.getScore();
                        String element = tuple.getElement();

                        System.out.println("score: " + score);
                        System.out.println("element: " + element);

                        Task task = JSON.parseObject(element, Task.class);
                        System.out.println(task);

                        if (System.currentTimeMillis() < score){
                            System.out.println(Thread.currentThread().getName() + " 任务未到期！sleep~~~");
                            Thread.sleep((long) (score - System.currentTimeMillis()));
                        }else{
                            System.out.println(Thread.currentThread().getName() + " 任务到期！插入工作者队列");
                            //任务到期，从zset中删除，插入工作队列
                            //为了保证执行操作的原子性，可以使用lua脚本，或者使用分布式锁,显然lua脚本效率更高
//                            moveTaskToWorkQueueUseDistributedLock();moveTaskToWorkQueueUseLuaScript
                            moveTaskToWorkQueueUseDistributedLock(jedis, score, task);
                        }
                    }
                }

            }catch (Exception e){
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        }

        private boolean moveTaskToWorkQueueUseDistributedLock(Jedis jedis, double score, Task task) {
            DistributedLock lock = null;
            String identifier = "";
            try{
                lock = new DistributedLock(jedis);
                identifier = lock.tryLock(QUEUE_NAME, 2000, 1000);
                jedis.zremrangeByScore(QUEUE_NAME, score, score);
                long r = jedis.rpush(task.getType(), JSON.toJSONString(task));
                System.out.println(Thread.currentThread().getName() + " moveTaskToWorkQueueUseDistributedLock 插入工作者队列");
                return r == 1;
            }finally {
                if (lock !=null){
                    lock.releaseLock(QUEUE_NAME, identifier);
                }
            }

        }

    }

    private class WorkRunnable implements Runnable{

        private String queueName;

        private JedisPool jedisPool;

        private static final int DEFAULT_TIMEOUT = 10 * 1000;

        public WorkRunnable(JedisPool jedisPool, String queueName){
            this.queueName = queueName;
            this.jedisPool = jedisPool;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 工作者" + queueName +"开始工作 --------->");
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                while (true){
                    List<String> tasks =  jedis.blpop(DEFAULT_TIMEOUT, queueName);
                    if (tasks != null){
                        System.out.println(Thread.currentThread().getName() + " 工作者" + queueName +"获取到任务 "+ tasks.size() +" ，开始执行。。。");
                        for (int i = 0; i < tasks.size(); i++){
                            Thread.sleep(20);
                            System.out.println(Thread.currentThread().getName() + " 工作者" + queueName +"任务执行完成" + i);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " 工作者" + queueName +"任务执行失败");
            }
            finally {
                if (jedis != null){
                    jedis.close();
                }
            }
        }

    }

}



















