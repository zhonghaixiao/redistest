package com.example.redistest.dao.mapper;

import com.example.redistest.dao.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ActivityDao {

    Activity getActivity(String activityId);

}
