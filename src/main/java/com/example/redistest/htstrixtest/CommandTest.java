package com.example.redistest.htstrixtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CommandTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandTest.class);

    public static void main(String[] args) throws Exception {
        CommandOrder commandPhone = new CommandOrder("收集");
        CommandOrder command = new CommandOrder("电视");
        String execute = commandPhone.execute();
        LOGGER.info("execute=[{}]", execute);

        Future<String> queue = command.queue();
        String value = queue.get(200, TimeUnit.MILLISECONDS);
        LOGGER.info("value = [{}]", value);

        CommandUser commandUser = new CommandUser("张三");
        String name = commandUser.execute();
        LOGGER.info("name=[{}]", name);
    }
}
