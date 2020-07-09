package com.example.redistest.dubbo.t1;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
//        创建一个 ServiceConfig 的实例，泛型参数信息是服务接口类型，即 GreetingsService。
        ServiceConfig<GreetingService> service = new ServiceConfig<>();
//        生成一个 AplicatonConfig 的实例，并将其装配进 ServiceConfig。
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
//        生成一个 RegistryConfig 实例，并将其装配进 ServiceConfig，这里使用的是组播方式，
//        参数是 multicast://224.5.6.7:1234。合法的组播地址范围为：224.0.0.0 - 239.255.255.255
        service.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
//        service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
//        将服务契约 GreetingsService 装配进 ServiceConfig。
        service.setInterface(GreetingService.class);
//        将服务提供者提供的实现 GreetingsServiceImpl 的实例装配进 ServiceConfig。
        service.setRef(new GreetingServiceImpl());
//        ServiceConfig 已经具备足够的信息，开始对外暴露服务，默认监听端口是 20880。
        service.export();
        System.out.println("first-dubbo-provider is running.");
//        为了防止服务端退出，按任意键或者 ctrl-c 退出。
        System.in.read();
    }
}