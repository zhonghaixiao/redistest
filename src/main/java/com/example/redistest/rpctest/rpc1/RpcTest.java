package com.example.redistest.rpctest.rpc1;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpcTest {

    public static void main(String[] args){
        System.out.println(HelloService.class.getName());
        new Thread(()->{
            try {
                Server serviceServer = new ServiceCenter(8888);
                serviceServer.register(HelloService.class, HelloServiceImpl.class);
                serviceServer.start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
        HelloService service = RpcClient.getRemoteProxyObj(HelloService.class,
                new InetSocketAddress("127.0.0.1", 8888));
        System.out.println(service.sayHi("zhong"));
        System.out.println(service.findUserByName("仲海啸"));
    }

}












