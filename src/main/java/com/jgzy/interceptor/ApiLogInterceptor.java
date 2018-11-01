package com.jgzy.interceptor;

import com.jgzy.constant.DatePatternConstant;
import com.jgzy.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.util.UUID;

@Component
@Aspect
@Order(0)
public class ApiLogInterceptor {


    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String TRACE_ID = "traceId";
    private final static String SESSION_ID = "sessionId";

    /**
     * 设置 切入点
     */
    @SuppressWarnings("unused")
    @Pointcut("execution(* com.jgzy.core.*.controller.*.*(..))")
    private void pointCutMethod() {
    }

    //声明环绕通知
    @Around("pointCutMethod()")
    Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        long begin = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("\r\n");
        try {
            Class<?> classTarget = joinPoint.getTarget().getClass();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            sb.append("[方法]").append(classTarget).append(".").append(signature.getName()).append("() ").append("开始时间:").append(DateUtil.formatTimeInMillis(begin)).append("\r\n");

            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                sb.append("[参数]");
                for (Object obj : joinPoint.getArgs()) {
                    try {
                        if (obj instanceof MultipartFile) {
                            MultipartFile file = (MultipartFile) obj;
                            sb.append(file.getOriginalFilename()).append("[").append(file.getSize() / 1024).append("KB],");
                        } else if (obj instanceof File) {
                            File file = (File) obj;
                            sb.append(file.getName()).append("[").append(file.length() / 1024).append("KB],");
                        } else if (obj instanceof ServletRequest || obj instanceof ServletResponse) {
//                            sb.append().append("").append().append("");
                            sb.append(obj.getClass()).append(",");

                        } else {
                            sb.append(JSON.toJSONString(obj)).append(",");

                        }
                    } catch (Exception e) {
                        //转json异常后直接toString
                        sb.append(obj == null ? "null" : obj).append(",");
                    }
                }
                int index = sb.lastIndexOf(",");
                if (index > 0) {
                    sb.replace(index, index + 1, "");
                }
                sb.append("\r\n");
            }

            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            sb.append("[返回]").append(result == null ? "null" : JSON.toJSONString(result)).append("\r\n");
            sb.append("[耗时]").append((end - begin)).append("ms\r\n");
            logger.info(sb.toString());
            logger.error(" this is error time: "+DateUtil.formatDate(System.currentTimeMillis(), DatePatternConstant.COMMON_DATE_AND_TIME));
            logger.warn(" this is warn time: "+DateUtil.formatDate(System.currentTimeMillis(), DatePatternConstant.COMMON_DATE_AND_TIME));

            return result;
        } catch (Exception e) {
            sb.append("[异常]").append(e.getMessage());
            logger.warn(sb.toString());
            throw e;
        }
    }
}
