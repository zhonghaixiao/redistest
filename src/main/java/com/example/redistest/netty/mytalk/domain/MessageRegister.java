package com.example.redistest.netty.mytalk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRegister extends BaseMessage {
    private String boxId;
    private String ip;
    private String mac;

    @Override
    public byte getType() {
        return REGISTER;
    }
}
