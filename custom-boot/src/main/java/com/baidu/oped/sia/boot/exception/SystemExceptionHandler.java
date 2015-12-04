package com.baidu.oped.sia.boot.exception;


import com.baidu.oped.sia.boot.common.BasicResponse;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.INTERNAL_SYS_ERROR;
import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.REQ_PARAM_MISMATCH;

@ControllerAdvice
public class SystemExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SystemExceptionHandler.class);

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<BasicResponse> handleSystemException(SystemException exception) {
        LOG.warn("SystemException handled", exception);
        SystemCode code = exception.getCode();
        BasicResponse error = new BasicResponse();
        String requestId = getRequestId();
        error.setRequestId(requestId);
        error.setCode(code);
        error.setMessage(getLocalMessage(exception.getMessage()));
        LOG.info("[exception] {}", error.getCode());
        if (code == SystemCode.INTERNAL_ERROR) {
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (code == SystemCode.INVALID_PARAMETER || code == SystemCode.INVALID_PARAMETER_VALUE
                || code == SystemCode.EXCEED_MAX_RETURN_DATA_POINTS
                || code == SystemCode.EXCEED_MAX_QUERY_DATA_POINTS) {
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (code == SystemCode.AUTHENTICATION_ERROR || code == SystemCode.AUTHORIZATION_ERROR) {
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BasicResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        LOG.warn("MissingServletRequestParameterException handled", exception);
        BasicResponse error = new BasicResponse();
        String requestId = getRequestId();
        error.setRequestId(requestId);
        error.setCode(SystemCode.INVALID_PARAMETER);
        Object[] args = new Object[]{exception.getParameterName(), exception.getParameterType()};
        error.setMessage(getLocalMessage(REQ_PARAM_MISMATCH, args));
        LOG.info("[exception] {}", error.getCode());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse> handleException(Exception exception) {
        LOG.error("Exception handled", exception);
        BasicResponse error = new BasicResponse();
        String requestId = getRequestId();
        error.setRequestId(requestId);
        error.setCode(SystemCode.INTERNAL_ERROR);
        error.setMessage(getLocalMessage(INTERNAL_SYS_ERROR));
        LOG.info("[exception] {}", error.getCode());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String getRequestId() {
        return RequestInfoHolder.getThreadTraceId();
    }

    private static String getLocalMessage(String key) {
        return getLocalMessage(key, null);
    }

    private static String getLocalMessage(String key, Object[] args) {
        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key, args);
    }
}
