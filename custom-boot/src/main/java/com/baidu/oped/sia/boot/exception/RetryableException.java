package com.baidu.oped.sia.boot.exception;

/**
 * Retryable Exception. Throw when the task need retry.
 * <p>
 * This Exception should only be thrown by remote process call.
 *
 * @author mason
 */
public class RetryableException extends InternalException {
}
