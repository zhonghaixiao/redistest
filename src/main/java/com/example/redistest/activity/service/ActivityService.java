package com.example.redistest.activity.service;

import com.example.redistest.dao.entity.Activity;
import com.example.redistest.dao.entity.ActivityState;
import com.example.redistest.dao.mapper.ActivityDao;
import com.example.redistest.delayqueue.redis.v1.RedisDelayQueueService;
import com.example.redistest.delayqueue.redis.v1.Task;
import com.example.redistest.delayqueue.redis.v1.TaskState;
import com.example.redistest.delayqueue.redis.v1.WorkerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    ActivityDao activityDao;

    @Autowired
    RedisDelayQueueService delayQueueService;

    public List<Activity> getActivityList(){
        return activityDao.getActivities();
    }

    public boolean newActivity(Activity activity){
        int r = activityDao.insertActivity(activity);
        if (r == 1){
            delayQueueService.addDelayTask(Task.builder()
                            .id(UUID.randomUUID().toString())
                            .type("startActivity")
                            .state(TaskState.WAIT)
                            .executeMillis(activity.getStartTime().getMillis())
                            .payload(activity.getId()).build()
                    , activity.getStartTime().getMillis() - System.currentTimeMillis()
                    , new WorkerCallback() {
                        @Override
                        public void run(String activityId) {
                            beginActivityByStartTime(activityId);
                        }
                    });

            delayQueueService.addDelayTask(Task.builder()
                            .id(UUID.randomUUID().toString())
                            .type("endActivity")
                            .state(TaskState.WAIT)
                            .executeMillis(activity.getStartTime().getMillis())
                            .payload(activity.getId()).build()
                    , activity.getEndTime().getMillis() - System.currentTimeMillis()
                    , new WorkerCallback() {
                        @Override
                        public void run(String activityId) {
                            endActivityByEndTime(activityId);
                        }
                    });
        }
        return r == 1;
    }

    public boolean beginActivityByStartTime(String activityId){
        Activity activity = activityDao.getActivity(activityId);
        if (activity != null){
            if (activity.getStartTime().isBeforeNow()){
                activity.setState(ActivityState.IN_PROGRESS);
                int i = activityDao.updateActivity(activity);
                return i == 1;
            }else{
                throw new RuntimeException("当前task未开始");
            }
        }
        return false;
    }

    public boolean endActivityByEndTime(String activityId){
        Activity activity = activityDao.getActivity(activityId);
        if (activity != null){
            if (activity.getEndTime().isBeforeNow()){
                activity.setState(ActivityState.END);
                int i = activityDao.updateActivity(activity);
                return i == 1;
            }else{
                throw new RuntimeException("当前task未结束");
            }
        }
        return false;
    }

}
