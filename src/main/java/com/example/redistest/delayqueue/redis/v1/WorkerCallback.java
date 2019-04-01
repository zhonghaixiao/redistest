package com.example.redistest.delayqueue.redis.v1;

public interface WorkerCallback {

    void run(String activityId);

}
