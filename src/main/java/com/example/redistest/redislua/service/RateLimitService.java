package com.example.redistest.redislua.service;

import com.google.common.io.CharStreams;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RateLimitService {

    public boolean accessLimit(String ip, int limit, int timeout, Jedis connection) throws IOException {
        List<String> keys = Collections.singletonList(ip);
        List<String> argv = Arrays.asList(String.valueOf(limit), String.valueOf(timeout));

        return 1 == (long) connection.eval(loadScriptString("ratelimit.lua"), keys, argv);
    }

    // 加载Lua代码
    private String loadScriptString(String fileName) throws IOException {
        InputStream is = RateLimitService.class.getClassLoader().getResourceAsStream(fileName);
        Reader reader = new InputStreamReader(is);
        return CharStreams.toString(reader);
    }

}
