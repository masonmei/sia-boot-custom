//package com.baidu.oped.sia.boot.client.http;
//
//import org.slf4j.Logger;
//
//import java.util.HashMap;
//import java.util.Map;
//import javax.ws.rs.core.Response;
//
///**
// * 内部REST调用返回，一般client不会直接使用这个类型,如果使用记得调用getEntity或close，以关闭连接
// */
//public class InternalResponse {
//
//    private static Logger log = org.slf4j.LoggerFactory.getLogger("InternalResponse");
//
//    private Response response;
//
//    int status;
//    Map<String, String> headers = new HashMap<>();
//
//    public InternalResponse(Response response) {
//        this.response = response;
//        status = response.getStatus();
//        for (String key : response.getHeaders().keySet()) {
//            headers.put(key, response.getHeaderString(key));
//        }
//    }
//
//    public <T> T getEntity(Class<T> returnType) {
//        try {
//            T result = response.readEntity(returnType);
//            // 文档上readEntity会关闭连接，此处再强制关闭一下，确保链接关闭
//            response.close();
//            return result;
//        } finally {
//            response.close();
//        }
//    }
//
//    public void close() {
//        response.close();
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public String header(String name) {
//        return headers.get(name);
//    }
//
//    public Map<String, String> headers() {
//        return headers;
//    }
//}
