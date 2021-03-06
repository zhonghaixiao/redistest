package com.example.redistest.dao.mapper;

import com.example.redistest.dao.entity.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityMapperTest {

    @Autowired
    ActivityDao activityDao;

    @Test
    public void getActivity() {

        Activity activity = activityDao.getActivity("123");
        System.out.println(activity);

        activity.setName("test");
        activityDao.updateActivity(activity);

        System.out.println(activityDao.getActivity("123"));

        activity.setId(UUID.randomUUID().toString());
        activityDao.insertActivity(activity);

    }


}