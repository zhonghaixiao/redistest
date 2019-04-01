package com.example.redistest.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private String id;
    private String name;
    private ActivityState state;
    private DateTime startTime;
    private DateTime endTime;
    private DateTime createTime;
    private DateTime updateTime;

}
