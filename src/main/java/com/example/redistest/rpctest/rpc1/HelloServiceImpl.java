package com.example.redistest.rpctest.rpc1;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }

    @Override
    public User findUserByName(String name) {
        return new User(name, 27);
    }
}
