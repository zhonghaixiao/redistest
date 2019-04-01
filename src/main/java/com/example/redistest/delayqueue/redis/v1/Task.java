package com.example.redistest.delayqueue.redis.v1;

import com.example.redistest.dao.entity.Activity;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String id;
    private String type;
    private TaskState state;
    private long executeMillis;
    private String payload;

}
