package com.example.redistest.rpctest.rpc2;

import com.example.redistest.rpctest.rpc2.client.RpcProxy;
import com.example.redistest.rpctest.rpc2.client.ServiceDiscovery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rpc2Config {

    @Value("${registry.address}")
    String registryAddress;

    @Value("${rpc.server.address}")
    String serverAddress;

    @Bean
    ServiceRegistry serviceRegistry(){
        return new ServiceRegistry(registryAddress);
    }

    @Bean
    RpcServer rpcServer(){
        return new RpcServer(serverAddress, serviceRegistry());
    }

    @Bean
    ServiceDiscovery serviceDiscovery(){
        return new ServiceDiscovery(registryAddress);
    }

    @Bean
    RpcProxy rpcProxy(){
        return new RpcProxy(serviceDiscovery());
    }

}
