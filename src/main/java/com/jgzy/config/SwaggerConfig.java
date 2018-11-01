package com.jgzy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    private Environment env;

    @Bean
    Docket personalCenter() {
        List<Parameter> pars = setHeader();
        return setDocket("com.jgzy.core.personalCenter", "personalCenter", pars);
    }

    @Bean
    Docket shopOrder() {
        List<Parameter> pars = setHeader();
        return setDocket("com.jgzy.core.shopOrder", "shopOrder", pars);
    }

//    @Bean
//    Docket example() {
//        List<Parameter> pars = setHeader();
//        return setDocket("com.jgzy.core.example", "example", pars);
//    }

    private Docket setDocket(String path, String groupName) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping(env.getProperty("doc.api.basePath"))
                .select()
//                .paths(PathSelectors.regex("/api/.*"))
//                .paths(PathSelectors.none())//如果是线上环境，添加路径过滤，设置为全部都不符合
                .apis(RequestHandlerSelectors.basePackage(path))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private Docket setDocket(String path, String groupName, List<Parameter> pars) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping(env.getProperty("doc.api.basePath"))
                .select()
//                .paths(PathSelectors.regex("/api/.*"))
//                .paths(PathSelectors.none())//如果是线上环境，添加路径过滤，设置为全部都不符合
                .apis(RequestHandlerSelectors.basePackage(path))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private List<Parameter> setHeader() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        // TODO 测试用添加token
        return new ApiInfo("前置接口 token:4f797d70f2b548c190f8a4cdee233b93",//大标题
                "内部接口",//小标题
                env.getProperty("doc.api.version"),
                env.getProperty("doc.api.termsOfServiceUrl"),
                env.getProperty("doc.api.contact"),
                env.getProperty("doc.api.license"),
                env.getProperty("doc.api.licenseUrl")
        );
    }


}