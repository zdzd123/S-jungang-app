package com.jgzy.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@MapperScan("com.jgzy.core.*.mapper*")
@Order(2)
public class MybatisPlusConfig {
    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
        performanceInterceptor.setMaxTime(1000);
        // SQL是否格式化 默认false
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }

    /**
     * mybatis-plus分页插件<br>
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        return paginationInterceptor;
    }

//
//    @Bean
//    public MetaObjectHandler metaObjectHandler(){
//        return new DefaultMetaObjectHandler();
//    }
//
//    /**
//     * 注入sql注入器
//     */
//    @Bean
//    public ISqlInjector sqlInjector(){
//        return new LogicSqlInjector();
//    }

}