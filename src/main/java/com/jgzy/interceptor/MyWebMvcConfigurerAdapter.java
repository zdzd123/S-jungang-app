package com.jgzy.interceptor;

import com.alibaba.fastjson.JSON;
import com.jgzy.constant.ErrorCodeEnum;
import com.jgzy.constant.RedisConstant;
import com.jgzy.entity.common.ResultWrapper;
import com.jgzy.entity.common.UserUuidThreadLocal;
import com.jgzy.entity.po.UserInfo;
import com.jgzy.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 登录拦截
 * @author: zou
 * @create: 2018-10-17
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/api/login/detail");
        addInterceptor.excludePathPatterns("/api/constant/**");
        addInterceptor.excludePathPatterns("/api/**/constant/**");
        // 拦截配置
        addInterceptor.addPathPatterns("/api/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            if (handler instanceof HandlerMethod) {
                String token = request.getHeader("token");
                // 没有传入token
                if (StringUtils.isEmpty(token)) {
                    setResponseMsg(response);
                    return false;
                }
                // token不存在
                boolean flag = RedisUtil.hHasKey(RedisConstant.REDIS_USER_KEY, token);
                if (!flag) {
                    setResponseMsg(response);
                    return false;
                }
                // 登录成功后将user对象放置在本地线程中，方便controller和service获取
                UserUuidThreadLocal.set((UserInfo) RedisUtil.hget(RedisConstant.REDIS_USER_KEY, token));
//                UserInfo userInfo = new UserInfo();
//                userInfo.setId(1);
//                UserUuidThreadLocal.set(userInfo);
            }
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            /*
             * tomcat底层 每一个请求都是一个线程，如果每一个请求都启动一个线程，性能就会降低，
             * 1.于是就有了线程池，而线程池中的线程并不是真正销毁或真正启动的。
             * 2.也就是说这个请求的线程是个可复用的线程，第二次请求可能还会拿到刚刚的线程，
             * 3. 若不清空，里面本身就有uuid，数据会错乱
             */
            // 清空本地线程中的uuid对象数据
            UserUuidThreadLocal.set(null);
//            UserUuidThreadLocal.remove();
            super.afterCompletion(request, response, handler, ex);
        }
    }

    /**
     * 设置response
     *
     * @param response
     * @throws IOException
     */
    public void setResponseMsg(HttpServletResponse response)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ResultWrapper<String> result = new ResultWrapper<>();
        result.setErrorCode(ErrorCodeEnum.ERROR_LOGIN_OUT.getKey());
        result.setErrorMsg(ErrorCodeEnum.ERROR_LOGIN_OUT.getValue());
        result.setSuccessful(false);
        writer.append(JSON.toJSONString(result));
    }


}