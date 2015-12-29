package com.baidu.oped.sia.boot.exception.internal;

import com.baidu.oped.sia.boot.exception.InternalException;

/**
 * Retry Exception. Throw when the task need retry.
 * This Exception should only be thrown by remote process call.
 * </p>
 *
 * @author mason
 */
public class RetryableException extends InternalException {
}
