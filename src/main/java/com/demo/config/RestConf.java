package com.demo.config;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;


/**
 * @Description TODO
 * @Author AllenPeng
 * @Date 2019-07-08 21:34
 */
@Configuration
public class RestConf {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate =new RestTemplate();
        //连接池
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(20);
        pool.setDefaultMaxPerRoute(20);
        //重试处理
        HttpRequestRetryHandler handler = (exception,count,context)->{
            if (count >=5){// 如果已经重试了5次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };
        //构造client
        HttpClientBuilder builder= HttpClients.custom()
                .setConnectionManager(pool)
                .setRetryHandler(handler)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);
        CloseableHttpClient httpClient= builder.build();
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(6000);
        factory.setReadTimeout(6000);
        factory.setConnectionRequestTimeout(6000);
        restTemplate.setRequestFactory(factory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }
}
