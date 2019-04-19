package com.example.redistest.netty.mytalk.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 跳过魔数与版本号
        in.skipBytes(5);
        byte serializeAlgorithm = in.readByte();
        byte cmd = in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Message<Object> message = JSON.parseObject(bytes, Message.class);
        out.add(message);
    }

}
