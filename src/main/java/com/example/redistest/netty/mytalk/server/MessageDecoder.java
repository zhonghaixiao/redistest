package com.example.redistest.netty.mytalk.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import mytalk.domain.BaseMessage;
import mytalk.domain.MRegisterRes;
import mytalk.domain.Message;
import mytalk.domain.MessageRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDecoder extends ByteToMessageDecoder {

    private Map<Byte, Class> messageMap;

    public MessageDecoder(){
        messageMap = new HashMap<>();
        messageMap.put(BaseMessage.PEER_TO_PEER, Message.class);
        messageMap.put(BaseMessage.REGISTER, MessageRegister.class);
        messageMap.put(BaseMessage.REGISTER_RES, MRegisterRes.class);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 跳过魔数与版本号
        in.skipBytes(5);
        byte serializeAlgorithm = in.readByte();
        byte cmd = in.readByte();
        byte type = in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        BaseMessage message = JSON.parseObject(bytes, messageMap.get(type));
        out.add(message);


    }

}
