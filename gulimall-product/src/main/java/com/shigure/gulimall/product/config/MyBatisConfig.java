package com.shigure.gulimall.product.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration     //标注是一个配置类
@EnableTransactionManagement       //开启事务功能
@MapperScan("com.shigure.gulimall.product.dao")
public class MyBatisConfig {
    //引入分页插件

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        interceptor.setOverflow(true);
        //interceptor.addInnerInterceptor(new PaginationInterceptor(DbType.H2));
        interceptor.setLimit(1000);
        return interceptor;
    }
}
