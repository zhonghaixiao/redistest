package com.example.redistest.netty.mytalk.client;

import com.example.redistest.netty.mytalk.server.Message;
import com.example.redistest.netty.mytalk.server.MessageDecoder;
import com.example.redistest.netty.mytalk.server.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TalkClient {

    public static void main(String[] args){
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(worker)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageEncoder())
                                    .addLast(new MessageDecoder())
                                    .addLast(new TestClientHandler());
                        }
                    });
            Channel channel = b.connect("127.0.0.1", 8000).sync().channel();
            Message m = Message.builder().from("zhong").to("haixiao").body("hello world").build();
            while (true){
                channel.writeAndFlush(m);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
