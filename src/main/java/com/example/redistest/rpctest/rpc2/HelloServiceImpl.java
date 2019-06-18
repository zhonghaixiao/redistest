package com.example.redistest.rpctest.rpc2;

@RpcService(HelloService.class)//指定远程接口
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello! " + name;
    }
}
