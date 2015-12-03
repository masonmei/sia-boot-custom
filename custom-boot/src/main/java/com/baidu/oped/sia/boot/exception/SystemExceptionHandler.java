package com.baidu.oped.sia.boot.exception;


import com.baidu.oped.sia.boot.common.BasicResponse;
import com.baidu.oped.sia.boot.common.RequestInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.INTERNAL_SYS_ERROR;
import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.REQ_PARAM_MISMATCH;

@ControllerAdvice
public class SystemExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SystemExceptionHandler.class);

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    private String getRequestId() {
        return RequestInfoHolder.getThreadTraceId();
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<BasicResponse> handleSystemException(HttpServletRequest request, HttpServletResponse response,
                                                               SystemException exception) {
        LOG.warn("SystemException handled", exception);
        SystemCode code = exception.getCode();
        BasicResponse error = new BasicResponse();
        String requestId = getRequestId();
        error.setRequestId(requestId);
        error.setCode(code);
        error.setMessage(buildMessage(exception, request));
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
            HttpServletRequest request, HttpServletResponse response,
            MissingServletRequestParameterException exception) {
        LOG.warn("MissingServletRequestParameterException handled", exception);
        BasicResponse error = new BasicResponse();
        String requestId = getRequestId();
        error.setRequestId(requestId);
        error.setCode(SystemCode.INVALID_PARAMETER);
        Object[] args = new Object[]{exception.getParameterName(), exception.getParameterType()};
        error.setMessage(buildMessage(REQ_PARAM_MISMATCH, args, request));
        LOG.info("[exception] {}", error.getCode());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse> handleException(
            HttpServletRequest request, HttpServletResponse response, Exception exception) {
        LOG.error("Exception handled", exception);
        BasicResponse error = new BasicResponse();
        String requestId = getRequestId();
        error.setRequestId(requestId);
        error.setCode(SystemCode.INTERNAL_ERROR);
        error.setMessage(buildMessage(request));
        LOG.info("[exception] {}", error.getCode());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String buildMessage(SystemException exception, HttpServletRequest request) {
        return buildMessage(exception.getMessage(), exception.getArgs(), request);
    }

    private String buildMessage(HttpServletRequest request) {
        return buildMessage(INTERNAL_SYS_ERROR, request);
    }

    private String buildMessage(String messageId, HttpServletRequest request) {
        return buildMessage(messageId, null, request);
    }

    private String buildMessage(String messageId, Object[] args, HttpServletRequest request) {
        try {
            return messageSource.getMessage(messageId, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException exception) {
            return messageId;
        }
    }
}
