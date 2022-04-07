package com.shigure.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//1.整合mybatisplus
//1.1导入依赖
//1.2.1配置数据源
//1.2.2配置mybatis-plus:使用@MapperScan，告诉Mybatis-Plus，sql映射文件位置
@EnableFeignClients(basePackages = "com.shigure.gulimall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.shigure.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
