package com.example.redistest.dubbo.t1;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class Client {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
//        创建一个 ReferenceConfig 的实例，同样，泛型参数信息是服务接口类型，即 GreetingService。
        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
//        生成一个 AplicatonConfig 的实例，并将其装配进 ReferenceConfig。
        reference.setApplication(new ApplicationConfig("first-dubbo-client"));
//        生成一个 RegistryConfig 实例，并将其装配进 ReferenceConfig，注意这里的组播地址信息需要与服务提供方的相同。
//        reference.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
//        将服务契约 GreetingsService 装配进 ReferenceConfig。
        reference.setInterface(GreetingService.class);
//        reference.setGeneric(true);
//        reference.setInterface("org.apache.dubbo.samples.api.GreetingsService");
//        从 ReferenceConfig 中获取到 GreetingService 的代理。
        GreetingService greetingService = reference.get();

        String message = greetingService.sayHello("zhonghaixiao");
        System.out.println(message);
    }
}
