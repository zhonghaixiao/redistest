package com.example.redistest.delayqueue.redis.v1;

public interface WorkerCallback {

    boolean run(String activityId);

}
