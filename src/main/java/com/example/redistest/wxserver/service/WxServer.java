package com.example.redistest.wxserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.redistest.wxserver.domain.AccessToken;
import com.example.redistest.wxserver.exception.WxException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxServer {

    private static final String appid = "wx3dfc73c221955438";
    private static final String appsecret = "98caf42430649bf4546d68d944e20834";

    private static final String apiDomain = "https://api.weixin.qq.com";
    private static final String apiDomainBackUp = "https://api.weixin.qq.com";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JedisPool jedisPool;

    private AccessToken getAccessTokenFromRemote(){
        String uri= apiDomain + "/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity(headers);
        String strbody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
        AccessToken token= JSONObject.parseObject(strbody, AccessToken.class);
        try{
            if (token != null && token.getAccessToken() == null){
                throw new WxException();
            }
        }catch (WxException e){
            log.error("get access token error, handle the exception. {}", e);
        }
        log.info("get a new access token : {}", token);

        return token;
    }

    public AccessToken getAccessToken(){
        AccessToken accessToken = getAccessTokenFromRedis();
        if (accessToken == null){
            log.info("could not find access token in redis");
            accessToken = getAccessTokenFromRemote();
        }
        return accessToken;
    }

    private AccessToken getAccessTokenFromRedis(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String accessTokenStr = jedis.get("access_token");
            log.info("get access token from redis = {}", accessTokenStr);
            if (StringUtils.isEmpty(accessTokenStr)){
                return null;
            }else{
                return JSON.parseObject(accessTokenStr, AccessToken.class);
            }
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    private boolean writeAccessTokenIntoRedis(String accessTokenStr){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String r = jedis.set("access_token", accessTokenStr);
            return StringUtils.equals("1", r);
        }finally {
            jedis.close();
        }
    }

}
