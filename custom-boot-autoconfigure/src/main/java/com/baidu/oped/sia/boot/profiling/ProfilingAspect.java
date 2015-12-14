package com.baidu.oped.sia.boot.profiling;

import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * Aspect for profiling purpose.
 * <p>
 * Created by mason on 11/6/15.
 */
@Aspect
public class ProfilingAspect {

    public static final int DEFAULT_WARNING_THRESHOLD_IN_MILLIS = 5000;
    public static final int DEFAULT_LOG_STATISTICS_FREQUENCY = 100;

    private static final Logger log = LoggerFactory.getLogger(ProfilingAspect.class);
    private static final ConcurrentHashMap<String, MethodStat> METHOD_STATS_MAP = new ConcurrentHashMap<>(500);

    private final long methodWarningThreshold;
    private final long logFrequency;

    public ProfilingAspect(long methodWarningThreshold, long logFrequency) {
        if (methodWarningThreshold <= 0) {
            methodWarningThreshold = DEFAULT_WARNING_THRESHOLD_IN_MILLIS;
        }

        if (logFrequency <= 0) {
            logFrequency = DEFAULT_LOG_STATISTICS_FREQUENCY;
        }

        this.methodWarningThreshold = methodWarningThreshold;
        this.logFrequency = logFrequency;
    }

    @Pointcut("@annotation(com.baidu.oped.sia.boot.profiling.Profiling)")
    public void annotatedProfiling() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Around("(publicMethod() && annotatedProfiling()) || controller()")
    public Object profiling(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        String methodName = joinPoint.getSignature().toString();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("process method: {}, using: {}ms", methodName, stopWatch.getTotalTimeMillis());
            if (stopWatch.getTotalTimeMillis() > methodWarningThreshold) {
                log.warn("found slow method execution: methodName : {}, elapsed: {}ms", methodName,
                        stopWatch.getTotalTimeMillis());
            }

            updateStat(methodName, stopWatch.getTotalTimeMillis());
        }
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    private void logStats(MethodStat stat) {
        long avgTime = stat.totalTime / stat.count;
        log.info("MethodStat: [methodName: {}, totalTime: {}ms, execution:{}, averageTime: {}ms, min:{}ms, max:{}ms]",
                stat.methodName, stat.totalTime, stat.count, avgTime, stat.minTime, stat.maxTime);
        stat.reset();
    }

    private void updateStat(String methodName, long elapsedInMillis) {
        MethodStat methodStat = METHOD_STATS_MAP.get(methodName);
        if (methodStat == null) {
            METHOD_STATS_MAP.put(methodName, new MethodStat(methodName));
            methodStat = METHOD_STATS_MAP.get(methodName);
        }

        methodStat.count++;
        methodStat.totalTime += elapsedInMillis;
        if (elapsedInMillis > methodStat.maxTime) {
            methodStat.maxTime = elapsedInMillis;
        }

        if (elapsedInMillis < methodStat.minTime) {
            methodStat.minTime = elapsedInMillis;
        }

        if (methodStat.count % logFrequency == 0) {
            logStats(methodStat);
        }
    }

    static class MethodStat {
        private final String methodName;
        private long count;
        private long totalTime;
        private long maxTime = Long.MIN_VALUE;
        private long minTime = Long.MAX_VALUE;

        public MethodStat(String methodName) {
            this.methodName = methodName;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public long getMaxTime() {
            return maxTime;
        }

        public void setMaxTime(long maxTime) {
            this.maxTime = maxTime;
        }

        public String getMethodName() {
            return methodName;
        }

        public long getMinTime() {
            return minTime;
        }

        public void setMinTime(long minTime) {
            this.minTime = minTime;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(long totalTime) {
            this.totalTime = totalTime;
        }

        public void reset() {
            count = 0;
            totalTime = 0;
            maxTime = Long.MIN_VALUE;
            minTime = Long.MAX_VALUE;
        }
    }
}
