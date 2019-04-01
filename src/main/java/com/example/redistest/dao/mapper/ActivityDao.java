package com.example.redistest.dao.mapper;

import com.example.redistest.dao.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ActivityDao {

    List<Activity> getActivities();

    Activity getActivity(String activityId);

    int insertActivity(Activity activity);

    int updateActivity(Activity activity);

}
