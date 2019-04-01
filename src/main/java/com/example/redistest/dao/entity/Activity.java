package com.example.redistest.dao.entity;

import lombok.Data;
import org.joda.time.DateTime;


@Data
public class Activity {

    private String id;
    private String name;
    private DateTime startTime;
    private DateTime endTime;
    private DateTime createTime;
    private DateTime updateTime;

}
