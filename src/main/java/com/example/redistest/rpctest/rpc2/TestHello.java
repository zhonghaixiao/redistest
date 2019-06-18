package com.example.redistest.rpctest.rpc2;

public class TestHello {
    private static TestHello ourInstance = new TestHello();

    public static TestHello getInstance() {
        return ourInstance;
    }

    private TestHello() {
    }
}
