package com.example.redistest.netty.mytalk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDisConnect<T> extends BaseMessage {

    private String boxId;

    @Override
    public byte getType() {
        return REGISTER;
    }
}
