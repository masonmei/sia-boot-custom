package com.baidu.oped.sia.boot.exception;


import static com.baidu.oped.sia.boot.exception.ExceptionArgsBuilder.get;
import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.INTERNAL_SYS_ERROR;
import static com.baidu.oped.sia.boot.exception.SystemCode.INTERNAL_ERROR;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceId;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceSourceIp;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceSourceSeq;
import static com.baidu.oped.sia.boot.utils.RequestUtils.getTraceStartTime;

import com.baidu.oped.sia.boot.common.BasicResponse;
import com.baidu.oped.sia.boot.exception.internal.InternalException;
import com.baidu.oped.sia.boot.exception.request.BadRequestException;
import com.baidu.oped.sia.boot.exception.request.UnsupportRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * System Exception Handler.
 *
 * @author mason
 */
@ControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SystemExceptionHandler.class);

    private static String getLocalMessage(String key) {
        return getLocalMessage(key, null);
    }

    private static String getLocalMessage(String key, Object[] args) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key, args);
    }

    /**
     * Handle All the exceptions to Server Error.
     *
     * @param exception un-category exception.
     * @return Response Entity.
     */
    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<BasicResponse> handleException(Exception exception) {
        LOG.error("Exception handled", exception);
        final String localMessage = getLocalMessage(INTERNAL_SYS_ERROR);
        final SystemCode systemCode = INTERNAL_ERROR;
        BasicResponse.Builder builder = generalResponseBuilder().code(systemCode).message(localMessage);
        LOG.info("[exception] {}, error Message: {}", systemCode, localMessage);
        return new ResponseEntity<>(builder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all the category system exception.
     *
     * @param exception system exception
     * @return Entity Response
     */
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<BasicResponse> handleSystemException(SystemException exception) {
        LOG.warn("SystemException handled", exception);
        final String localMessage = getLocalMessage(exception.getMessage());
        final SystemCode systemCode = exception.getCode();
        BasicResponse.Builder builder = generalResponseBuilder().code(systemCode).message(localMessage);
        LOG.info("[exception] {}, error Message: {}", systemCode, localMessage);

        if (systemCode == INTERNAL_ERROR) {
            return new ResponseEntity<>(builder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (systemCode == SystemCode.INVALID_PARAMETER || systemCode == SystemCode.INVALID_PARAMETER_VALUE
                || systemCode == SystemCode.EXCEED_MAX_RETURN_DATA_POINTS
                || systemCode == SystemCode.EXCEED_MAX_QUERY_DATA_POINTS) {
            return new ResponseEntity<>(builder.build(), HttpStatus.BAD_REQUEST);
        }

        if (systemCode == SystemCode.AUTHENTICATION_ERROR || systemCode == SystemCode.AUTHORIZATION_ERROR) {
            return new ResponseEntity<>(builder.build(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(builder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        LOG.error("Exception handled", ex);
        final SystemException exception = convertException(ex);
        final SystemCode systemCode = exception.getCode();
        final String localMessage = getLocalMessage(exception.getMessage());
        BasicResponse.Builder builder = generalResponseBuilder().code(systemCode).message(localMessage);
        LOG.info("[exception] {}, error Message: {}", systemCode, localMessage);

        return new ResponseEntity<>(builder.build(), headers, status);
    }

    private SystemException convertException(Exception ex) {
        if (ex instanceof NoSuchRequestHandlingMethodException || ex instanceof NoHandlerFoundException) {
            return new ResourceNotFoundException();
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) ex;
            return new UnsupportRequestException(
                    get().with("RequestMethod").comma(exception.getSupportedHttpMethods()).args());
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException exception = (HttpMediaTypeNotSupportedException) ex;
            return new UnsupportRequestException(
                    get().with("MediaType").comma(exception.getSupportedMediaTypes()).args());
        } else if (ex instanceof MissingServletRequestParameterException
                || ex instanceof HttpMediaTypeNotAcceptableException || ex instanceof ServletRequestBindingException
                || ex instanceof TypeMismatchException || ex instanceof HttpMessageNotReadableException
                || ex instanceof MethodArgumentNotValidException || ex instanceof MissingServletRequestPartException
                || ex instanceof BindException) {
            return new BadRequestException();
        } else {
            return new InternalException();
        }
    }

    private BasicResponse.Builder generalResponseBuilder() {
        return BasicResponse.builder().requestId(getTraceId()).traceStartTime(getTraceStartTime())
                .traceSourceIp(getTraceSourceIp()).traceSourceSeq(getTraceSourceSeq());
    }
}
