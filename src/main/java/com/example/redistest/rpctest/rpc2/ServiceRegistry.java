package com.example.redistest.rpctest.rpc2;

import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


public class ServiceRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private String registryAddress;

    public ServiceRegistry(String registryAddress){
        this.registryAddress = registryAddress;
    }

    public void register(String data){
        if (data != null){
            ZooKeeper zk = connectServer();
            if (zk != null){
                createNode(zk, data);
            }
        }
    }
}















