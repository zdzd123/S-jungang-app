package com.jgzy.interceptor;

import com.jgzy.constant.ErrorCodeEnum;
import com.jgzy.exception.ApiException;
import com.jgzy.entity.common.ResultWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResultWrapper handleException(Exception ex) {
        int errorCode = ErrorCodeEnum.ERROR.getKey();
        String errorMsg = ErrorCodeEnum.ERROR.getValue();

        if (ex instanceof UndeclaredThrowableException) {
            ex = getRealException((UndeclaredThrowableException) ex);
            errorCode = ErrorCodeEnum.ERROR_INNER.getKey();
            errorMsg = new StringBuilder().append(ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_INNER.getKey())).append(ex.getMessage()).toString();
        } else if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missing = (MissingServletRequestParameterException) ex;
            errorCode = ErrorCodeEnum.ERROR_ARGS_FILL_IN.getKey();
            errorMsg = new StringBuilder().append(missing.getParameterName()).append(ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_ARGS_FILL_IN.getKey())).toString();
        } else if (ex instanceof ServletRequestBindingException) {
            errorCode = ErrorCodeEnum.ERROR_ARGS_MISS.getKey();
            errorMsg = ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_ARGS_MISS.getKey());
            logger.error(ex.getMessage(), ex);
        } /*else if (ex instanceof TypeMismatchException || ex instanceof HttpMessageNotReadableException) {
            errorCode = ErrorCodeEnum.ERROR_ARGS_TYPE.getKey();
            errorMsg = ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_ARGS_TYPE.getKey());
        }*/ else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<ObjectError> FieldErrors = bindingResult.getAllErrors();
            StringBuilder errorMsgTmp = new StringBuilder();
            for (ObjectError oe : FieldErrors) {
                FieldError fe = (FieldError) oe;
                String field = fe.getField();
                String defaultMessage = fe.getDefaultMessage();
                errorMsgTmp.append(field).append("|").append(defaultMessage);
            }
            errorCode = ErrorCodeEnum.ERROR_INNER.getKey();
            errorMsg = new StringBuilder().append(ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_INNER.getKey())).append(errorMsgTmp.append(";")).toString();
        } else if (ex instanceof ApiException) {
            errorCode = ((ApiException) ex).getErrorCode();
            errorMsg = ((ApiException) ex).getErrorMsg();
        } else if (ex instanceof RedisConnectionFailureException) {
            errorCode = ErrorCodeEnum.ERROR_JEDIS_CONNECTION_EXCEPTION.getKey();
            errorMsg = new StringBuilder().append(ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_JEDIS_CONNECTION_EXCEPTION.getKey())).toString();
            logger.error(ex.getMessage(), ex);
        } else if (ex instanceof OptimisticLockingFailureException) {
            errorCode = ErrorCodeEnum.ERROR_INNER.getKey();
            String message = ex.getMessage();
            if (StringUtils.isNotEmpty(message)){
                errorMsg = message;
            }else {
                errorMsg = new StringBuilder().append(ErrorCodeEnum.desc(ErrorCodeEnum.ERROR_INNER.getKey())).toString();
            }
            logger.error(ex.getMessage(), ex);
        } else {
            logger.error(ex.getMessage(), ex);
        }


        return new ResultWrapper(errorCode, errorMsg);
    }

    Exception getRealException(UndeclaredThrowableException e) {
        if (e == null) {
            return null;
        }
        Throwable cause = e.getCause();
        if (cause instanceof UndeclaredThrowableException) {
            return getRealException((UndeclaredThrowableException) cause);
        }
        return (Exception) cause;
    }

}
