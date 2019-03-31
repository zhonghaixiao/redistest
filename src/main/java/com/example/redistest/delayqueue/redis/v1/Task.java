package com.example.redistest.delayqueue.redis.v1;

import lombok.*;

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

}
