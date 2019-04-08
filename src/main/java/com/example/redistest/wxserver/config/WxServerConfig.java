package com.example.redistest.wxserver.config;

import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
public class WxServerConfig {

    private static final String apiDomain = "api.weixin.qq.com";
    private static final String apiDomainBackUp = "api.weixin.qq.com";

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(apiDomain).build();
    }


}
