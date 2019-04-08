package com.example.redistest.activity.controller;

import com.example.redistest.config.Result;
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
    public Result getActivity(@PathVariable String activityId){
        return Result.ok(activityDao.getActivity(activityId));
    }

    @GetMapping("hello")
    public Result hello(){
        return Result.ok("hello");
    }

}
