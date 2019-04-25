package com.example.redistest.netty.mytalk.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> extends BaseMessage {
    private String from;
    private String to;
    private T body;

    @Override
    public byte getType() {
        return PEER_TO_PEER;
    }
}
