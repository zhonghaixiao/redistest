package com.example.redistest.netty.mytalk.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mytalk.domain.*;

import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class TestHandler extends SimpleChannelInboundHandler<BaseMessage> {

    long i = 0;

    private ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("remote address in , address" + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
//        System.out.println("get msg [" + i++ + "] : " + msg);
        if(msg instanceof MessageRegister){
            MessageRegister register = ((MessageRegister)msg);
            String box_id = register.getBoxId();
            channelMap.put(box_id, ctx.channel());
            ctx.channel().writeAndFlush(MRegisterRes.builder().result(1).build());

        }else if(msg instanceof Message){
            Message message = ((Message) msg);
            String to =  message.getTo();
            Channel toChannel = channelMap.get(to);
            toChannel.writeAndFlush(message);

        }else if(msg instanceof MessageDisConnect){
            channelMap.remove(((MessageDisConnect) msg).getBoxId());
        }
//        ctx.channel().writeAndFlush(msg + "\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
