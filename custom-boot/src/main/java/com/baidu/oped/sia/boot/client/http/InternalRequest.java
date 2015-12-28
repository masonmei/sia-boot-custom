package com.baidu.oped.sia.boot.client.http;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.baidu.oped.sia.boot.exception.InternalException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;

import com.baidu.oped.cloudwatch.business.common.DateFormat;
import com.baidu.oped.cloudwatch.business.common.SystemConstant;

import jersey.repackaged.com.google.common.base.Joiner;

public class InternalRequest {

    private Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    private Client client;
    private String endpoint;
    private StringBuilder path = new StringBuilder();
    private Map<String, List<Object>> headers = new HashMap<>();
    private Map<String, List<Object>> queryParams = new LinkedHashMap<>();
    private List<String> keyOnlyQueryParams = new ArrayList<>();

    private HttpMethod method = HttpMethod.GET;
    private String requestId;
    // 通过controller发送的请求都会设置threadRequestId，log中会统一输出requestId，设置logThreadRequestId为false
    public static final ThreadLocal<String> THREAD_REQUEST_ID = new ThreadLocal<>();
    // 系统内部发送的请求没有设置threadRequestId，log中不会统一输出requestId，需要在日志中特殊处理
    public static final ThreadLocal<Boolean> LOG_THREAD_REQUEST_ID = new ThreadLocal<>();

    private Date date = new Date();

    private String accept;

    private Entity<?> entity;

    private static final int MAX_RETRY_COUNT = 3;

    public InternalRequest(Client client) {
        this.client = client;
    }

    public String endpoint() {
        return endpoint;
    }

    public InternalRequest endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String path() {
        return path.toString();
    }

    public InternalRequest path(String path) {
        this.path.append(path);
        return this;
    }

    public Map<String, List<Object>> headers() {
        return headers;
    }

    public Map<String, String> headersAll() {
        Map<String, String> headersAll = new HashMap<String, String>();
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            headersAll.put(entry.getKey(), StringUtils.join(entry.getValue(), ";"));
        }
        return headersAll;
    }

    public InternalRequest header(String name, Object value) {
        if (value != null) {
            headers.put(name, Arrays.asList(value));
        }
        return this;
    }

    public InternalRequest requestId(String requestId) {
        this.requestId = requestId;
        header(SystemConstant.X_BCE_REQUEST_ID, requestId);
        return this;
    }

    public InternalRequest date(Date date) {
        this.date = date;
        return this;
    }

    public InternalRequest keyOnlyQueryParam(String key) {
        keyOnlyQueryParams.add(key);
        return this;
    }

    public InternalRequest action(String action) {
        queryParam("Action", action);
        return this;
    }

    public Map<String, List<Object>> queryParams() {
        return queryParams;
    }

    public Map<String, String> queryParamsAll() {
        Map<String, String> paramsAll = new HashMap<String, String>();
        for (Map.Entry<String, List<Object>> entry : queryParams.entrySet()) {
            paramsAll.put(entry.getKey(), StringUtils.join(entry.getValue(), ";"));
        }
        return paramsAll;
    }

    public InternalRequest queryParam(String key, List<String> values) {
        queryParam(key, Joiner.on(",").join(values));
        return this;
    }

    public InternalRequest queryParam(String key, Object value) {
        if (queryParams.containsKey(key)) {
            List<Object> values = queryParams.get(key);
            values.add(value);
        } else {
            List<Object> values = new ArrayList<>();
            values.add(value);
            queryParams.put(key, values);
        }
        return this;
    }

    public InternalRequest queryParams(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            queryParam(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public InternalRequest queryParams(Object obj) {
        Map<String, Object> map = ObjectMapperTool.map(obj);
        queryParams(map);
        return this;
    }

    public static String getThreadRequestId() {
        return THREAD_REQUEST_ID.get();
    }

    public static void setThreadRequestId(String requestId) {
        THREAD_REQUEST_ID.set(requestId);
    }

    public static void removeThreadRequestId() {
        THREAD_REQUEST_ID.remove();
    }

    public static void setLogThreadRequestId(Boolean isLogThreadRequestId) {
        LOG_THREAD_REQUEST_ID.set(isLogThreadRequestId);
    }

    public static void removeLogThreadRequestId() {
        LOG_THREAD_REQUEST_ID.remove();
    }

    public String accept() {
        return accept;
    }

    public InternalRequest accept(String accept) {
        this.accept = accept;
        return this;
    }

    public InternalRequest acceptJson() {
        this.accept = "application/json";
        return this;
    }

    public InternalRequest acceptXml() {
        this.accept = "application/xml";
        return this;
    }

    public InternalRequest acceptForm() {
        this.accept = "application/x-www-form-urlencoded";
        return this;
    }

    public HttpMethod method() {
        return method;
    }

    private InternalRequest method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Entity<?> entity() {
        return entity;
    }

    private InternalResponse execute() {
        prepareGlobalHeaders();

        String keyOnlyQueryParamsString = "";
        if (keyOnlyQueryParams.size() > 0) {
            keyOnlyQueryParamsString = "?" + keyOnlyQueryParams.get(0);
        }

        String requestIdLog = "[requestId:" + requestId + "] ";
        if (LOG_THREAD_REQUEST_ID.get() != null && !LOG_THREAD_REQUEST_ID.get().booleanValue()) {
            requestIdLog = "";
        }
        String requestInfo = "method:" + method + ",url:" + endpoint + path + keyOnlyQueryParamsString;
        log.info(requestIdLog + "http execute begin," + requestInfo);

        long timeBegin = System.currentTimeMillis();
        try {
            InternalResponse internalResponse = retryExecute(requestIdLog + requestInfo, MAX_RETRY_COUNT);
            long timeEnd = System.currentTimeMillis();
            long timeUsed = timeEnd - timeBegin;

            String responseRequestId = internalResponse.header(SystemConstant.X_BCE_REQUEST_ID);
            if (requestId != null && responseRequestId != null && !requestId.equals(responseRequestId)) {
                log.warn("responseRequestId is not equal with requestId,responseRequestId:{}," + "requestId:{},{}",
                        responseRequestId, requestId, requestInfo);
            }

            int status = internalResponse.getStatus();
            if (status >= 400) {
                InternalError error;
                try {
                    error = internalResponse.getEntity(InternalError.class);
                } catch (Exception ex) {
                    log.warn(requestIdLog + "http execute get response fail," + requestInfo + "," + "status:" + status
                            + ",response:" + internalResponse, ex);
                    error = new InternalError();
                    error.setCode("");
                    error.setMessage(ex.getMessage());
                } finally {
                    internalResponse.close();
                }

                log.info(requestIdLog + "http execute fail,{},status:{},code:{},time:{}ms", requestInfo,
                        internalResponse.getStatus(), error.getCode(), timeUsed);
                throw new InternalException();
            }

            log.info(requestIdLog + "http execute success,{},status:{},time:{}ms", requestInfo,
                    internalResponse.getStatus(), timeUsed);
            return internalResponse;
        } catch (ClientErrorException e) {
            long timeEnd = System.currentTimeMillis();
            long timeUsed = timeEnd - timeBegin;
            log.error(requestIdLog + "http execute exception,time:" + timeUsed + "ms" + requestInfo, e);
            throw new InternalException();
        }
    }

    private InternalResponse retryExecute(String logMessage, int retryCount) {
        int count = 1;
        while (true) {
            log.info("http retry execute begin,count:{},{}", count, logMessage);
            long timeBegin = System.currentTimeMillis();
            try {
                InternalResponse internalResponse = internalExecute();
                long timeEnd = System.currentTimeMillis();
                long timeUsed = timeEnd - timeBegin;
                log.info("http retry execute success,count:{},time:{}ms,{}", count, timeUsed, logMessage);
                return internalResponse;
            } catch (ProcessingException | WebApplicationException ex) {
                long timeEnd = System.currentTimeMillis();
                long timeUsed = timeEnd - timeBegin;
                log.warn("http retry execute exception,count:" + count + ",time:" + timeUsed + "ms" + logMessage,
                        ex);
                if (count > retryCount) {
                    throw ex;
                }
                if (!(ex.getCause() instanceof ConnectTimeoutException)
                        && !(ex.getCause() instanceof SocketTimeoutException)) {
                    throw ex;
                }
            }
            count++;
        }
    }

    private InternalResponse internalExecute() {
        String keyOnlyQueryParamsString = "";
        if (keyOnlyQueryParams.size() > 0) {
            keyOnlyQueryParamsString = "?" + keyOnlyQueryParams.get(0);
        }
        String realPath = path();
        WebTarget target = client.target(endpoint + keyOnlyQueryParamsString).path(realPath);
        for (Map.Entry<String, List<Object>> entry : queryParams.entrySet()) {
            for (Object o : entry.getValue()) {
                String realValue = String.valueOf(o);
                target = target.queryParam(entry.getKey(), realValue);
            }
        }

        Invocation.Builder invocation = target.request();
        for (Map.Entry<String, List<Object>> h : headers.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (Object v : h.getValue()) {
                sb.append(String.valueOf(v));
            }
            invocation.header(h.getKey(), sb);
        }
        if (accept != null) {
            invocation.accept(accept);
        }

        javax.ws.rs.client.Entity<?> internalEntity = null;
        if (this.entity != null) {
            internalEntity = javax.ws.rs.client.Entity.entity(this.entity.getEntity(), this.entity.getContentType());
        }
        if (this.entity == null && HttpMethod.PUT == method) {
            internalEntity = javax.ws.rs.client.Entity.entity("", MediaType.APPLICATION_JSON);
        }

        log.debug("jersey invoke begin");
        javax.ws.rs.core.Response response;
        if (internalEntity == null) {
            response = invocation.method(method.name());
        } else {
            response = invocation.method(method.name(), internalEntity);
        }
        log.debug("jersey invoke finish");
        return new InternalResponse(response);
    }

    private void prepareGlobalHeaders() {
        requestId = THREAD_REQUEST_ID.get();
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        Random rand = new Random();
        requestId = requestId + "-[" + rand.nextInt(100000) + "]";
        header(SystemConstant.X_BCE_REQUEST_ID, requestId);
        if (!headers.containsKey(SystemConstant.X_BCE_DATE)) {
            header(SystemConstant.X_BCE_DATE, DateFormat.getDateTimeFormat().format(date));
        }
    }

    /**
     * ********* httpMethod actions begin ***********
     */
    public void get() {
        getWitResponse().close();
    }

    /*
    必须调用BecInternalResponse.close()
     */
    public InternalResponse getWitResponse() {
        method(HttpMethod.GET);
        return execute();
    }

    public <T> T get(Class<T> returnType) {
        return getWitResponse().getEntity(returnType);
    }

    public void put() {
        putWithResponse().close();
    }

    public InternalResponse putWithResponse() {
        method(HttpMethod.PUT);
        return execute();
    }

    public InternalResponse putWithResponse(Entity<?> entity) {
        method(HttpMethod.PUT);
        this.entity = entity;
        return execute();
    }

    public void put(Entity<?> entity) {
        putWithResponse(entity).close();
    }

    public <T> T put(Entity<?> entity, Class<T> returnType) {
        return putWithResponse(entity).getEntity(returnType);
    }

    public <T> T put(Class<T> returnType) {
        return putWithResponse().getEntity(returnType);
    }

    public void post() {
        method(HttpMethod.POST);
        execute().close();
    }

    public void post(Entity<?> entity) {
        postWithResponse(entity).close();
    }

    public InternalResponse postWithResponse(Entity<?> entity) {
        method(HttpMethod.POST);
        this.entity = entity;
        return execute();
    }

    public <T> T post(Entity<?> entity, Class<T> returnType) {
        return postWithResponse(entity).getEntity(returnType);
    }

    public <T> T post(Class<T> returnType) {
        method(HttpMethod.POST);
        return execute().getEntity(returnType);
    }

    public InternalResponse deleteWithResponse() {
        method(HttpMethod.DELETE);
        return execute();
    }

    public void delete() {
        deleteWithResponse().close();
    }

    public <T> T delete(Class<T> returnType) {
        return deleteWithResponse().getEntity(returnType);
    }

    public InternalResponse patchWithResponse(Entity<?> entity) {
        method(HttpMethod.PATCH);
        this.entity = entity;
        return execute();
    }

    public void patch(Entity<?> entity) {
        patchWithResponse(entity).close();
    }

    public <T> T patch(Entity<?> entity, Class<T> returnType) {
        return patchWithResponse(entity).getEntity(returnType);
    }

    public InternalResponse headWithResponse() {
        method(HttpMethod.HEAD);
        return execute();
    }

    public void head() {
        headWithResponse().close();
    }

    public InternalResponse optionsWithResponse() {
        method(HttpMethod.OPTIONS);
        return execute();
    }

    public void options() {
        optionsWithResponse().close();
    }
}
