package com.example.redistest.netty.mytalk.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mytalk.domain.BaseMessage;
import mytalk.domain.MRegisterRes;
import mytalk.domain.Message;

public class TestClientHandler extends SimpleChannelInboundHandler<BaseMessage> {

    private TalkClientListener listener;

    public TestClientHandler(TalkClientListener listener){
        this.listener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
        switch (msg.getType()){
            case BaseMessage.REGISTER_RES:
                System.out.println("register success");
                listener.onRegisterResponse(((MRegisterRes)msg).getResult() == 1);
                break;
            case BaseMessage.PEER_TO_PEER:
                System.out.println("channelRead0 receive message: " + msg.toString());
                listener.onReceiveMessage((Message) msg);
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        listener.onRegisterDisconnect(ctx.channel().isActive());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        listener.onRegisterDisconnect(ctx.channel().isActive());
        cause.printStackTrace();
    }
}
