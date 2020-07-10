package com.example.redistest.netty.mytalk.domain;

public abstract class BaseMessage {

    public static final byte PEER_TO_PEER = 0x01;//"点对点消息";
    public static final byte REGISTER = 0x02;//"注册消息";
    public static final byte REGISTER_RES = 0x03;//"注册回复";

    public abstract byte getType();

}
