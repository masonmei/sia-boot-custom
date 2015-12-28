//package com.baidu.oped.sia.boot.client.http;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Map;
//
//public class ObjectMapperTool {
//
//    private static ObjectMapper mapper;
//
//    static {
//        initialize();
//    }
//
//    private static void initialize() {
//        mapper = new ObjectMapper();
//    }
//
//    public static Map<String, Object> map(Object obj) {
//        return mapper.convertValue(obj, Map.class);
//    }
//
//    public static <T> T map(Object obj, Class<T> tClass) {
//        return mapper.convertValue(obj, tClass);
//    }
//}
