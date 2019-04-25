package com.example.redistest.netty.mytalk.server;

public abstract class BaseMessage {

    public static final byte PEER_TO_PEER = 0x01;//"点对点消息";

    public abstract byte getType();

}
