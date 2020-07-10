package com.example.redistest.netty.mytalk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MRegisterRes extends BaseMessage {

    private int result;
    private String boxId;

    @Override
    public byte getType() {
        return REGISTER_RES;
    }
}
