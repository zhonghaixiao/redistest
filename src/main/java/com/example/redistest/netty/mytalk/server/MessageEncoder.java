package com.example.redistest.netty.mytalk.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import mytalk.domain.BaseMessage;

public class MessageEncoder extends MessageToByteEncoder<BaseMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf out) throws Exception {
        out.writeInt(0x12345678);
        out.writeByte(1);   //版本号
        out.writeByte(1);   //序列化算法
        out.writeByte(1);   //指令版本
        out.writeByte(msg.getType());   //指令类型
        byte[] msgBytes = JSON.toJSONBytes(msg);
        out.writeInt(msgBytes.length);
        out.writeBytes(msgBytes);
    }
}
