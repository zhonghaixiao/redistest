package com.example.redistest.activity.service;

import com.example.redistest.dao.entity.Activity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceTest {

    @Autowired
    ActivityService activityService;

    @Test
    public void newActivity() throws InterruptedException {

        for (int i = 0; i < 10; i++){
            boolean r = activityService.newActivity(
                    Activity.builder()
                            .id(UUID.randomUUID().toString())
                            .name("test" + i)
                            .startTime(DateTime.now().plusSeconds(i * 10))
                            .endTime(DateTime.now().plusSeconds(i * 10 + 20))
                            .build());
            if (r){
                System.out.println("add activity " + i + "successful");
            }else{
                System.out.println("add activity " + i + "failed");
            }
        }

        Thread.sleep(150*1000);

    }
}