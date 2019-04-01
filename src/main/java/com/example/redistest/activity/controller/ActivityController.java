package com.example.redistest.activity.controller;

import com.example.redistest.dao.entity.Activity;
import com.example.redistest.dao.mapper.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityDao activityDao;

    @GetMapping("{activityId}")
    public Activity getActivity(@PathVariable String activityId){
        return activityDao.getActivity(activityId);
    }

}
