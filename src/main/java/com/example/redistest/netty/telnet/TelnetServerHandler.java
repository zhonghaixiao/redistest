package com.example.redistest.netty.telnet;

import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;

@ChannelHandler.Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        boolean close = false;
        if (request.isEmpty()){
            response = "Please type something.\r\n";
        }else if ("bye".equals(request.toLowerCase())){
            response = "Have a good day!\r\n";
            close = true;
        }else{
            response = "Did you say '" + request + "'?\r\n";
        }
        ChannelFuture future = ctx.write(response);
        if (close){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
