package com.example.redistest.netty.mytalk.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import mytalk.domain.BaseMessage;
import mytalk.domain.Message;
import mytalk.domain.MessageRegister;
import mytalk.server.*;

import java.util.Scanner;
import java.util.UUID;

public class TalkClient implements TalkClientListener {

    private Channel channel;
    private String boxId;

    public void connect() throws InterruptedException {
        TalkClient cur = this;
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder())
                                .addLast(new MessageDecoder())
                                .addLast(new TestClientHandler(cur));
                    }
                });
        channel = b.connect("39.106.93.0", 8000).sync().channel();
//        channel = b.connect("127.0.0.1", 8000).sync().channel();
        channel.closeFuture().addListener(future -> {
            future.await();
            System.out.println("channel closed");
        });

    }

    public void sendMessage(BaseMessage message){
        channel.writeAndFlush(message);
    }

    public void register(){
        boxId = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("register generate boxId = " + boxId);
        BaseMessage m = MessageRegister.builder().boxId(boxId)
                .ip("122.122.122.122")
                .mac("mac111")
                .build();
        sendMessage(m);
    }

    @Override
    public void onRegisterResponse(boolean isSuccess) {
        System.out.println("register success : " + isSuccess);
    }

    @Override
    public void onReceiveMessage(Message message) {
        System.out.println("receive message: " + message);
    }

    @Override
    public void onRegisterDisconnect(boolean isConnected) {
        System.out.println("onRegisterDisconnect : " + isConnected);
    }

    public String getBoxId(){
        return boxId;
    }

    public static void main(String[] args){
        TalkClient client = new TalkClient();
        try {
            client.connect();
            client.register();
            Scanner scanner = new Scanner(System.in);
            System.out.println("waiting for input: ([to] [content]) ...");
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String to = line.split(" ")[0];
                String content = line.split(" ")[1];
                client.sendMessage(Message.builder().from(client.getBoxId()).to(to).body(content).build());
                System.out.println("waiting for input: ([to] [content]) ...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
