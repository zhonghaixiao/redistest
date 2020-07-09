package com.example.redistest.rxjavatest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

public class HelloCommand extends HystrixCommand<String> {

    private final String name;

    public HelloCommand(String name){
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "hello " + name + "!";
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }

    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }

    public static class UnitTest{

        @Test
        public void testSynchronous() throws ExecutionException, InterruptedException {
            Observable<String> fWorld = new HelloCommand("World").observe();
            Observable<String> fBob = new HelloCommand("Bob").observe();
            fWorld.subscribe((s)-> System.out.println(s));
        }

    }

}





















