package com.example.redistest.wxserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/")
@Slf4j
public class WxController {

    @GetMapping
    public String hello(@RequestParam String signature, @RequestParam String timestamp,
                        @RequestParam String nonce, @RequestParam String echostr){
        log.info("signature = {}, timestamp = {}, nonce = {}, echostr = {}", signature, timestamp, nonce, echostr);
        String[] params = new String[]{timestamp, nonce, "zhong"};
        Arrays.sort(params);
        StringBuilder sb = new StringBuilder();
        for (String s: params){
            sb.append(s);
        }
        log.info("toEncode = {}", sb.toString());
        String expectedSignature = DigestUtils.sha1Hex(sb.toString().getBytes());
        log.info("expectedSignature = {}", expectedSignature);
        if (StringUtils.equals(signature, expectedSignature)){
            return echostr;
        }else{
            return "";
        }
    }

}
