package com.jgzy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableCaching(proxyTargetClass = true) // 开启缓存功能
public class MyProducerStart {
    /**
     * druid连接池URL：http://localhost:2222/druid/weburi.html
     * 注册中心URL：http://localhost:1111/
     * swagger2URL：http://localhost:2222/swagger-ui.html
     * 登录URL：http://localhost:2222/control/userBase/login?userName=root&password=root
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(MyProducerStart.class).web(true).run(args);
    }

}
