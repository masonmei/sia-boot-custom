package com.baidu.oped.sia.boot.exception;

/**
 * Base Exception of all the runtime Exceptions.
 *
 * @author meidongxu@baidu.com
 */
public abstract class SystemException extends RuntimeException {

    private final SystemCode code;
    private Object[] args;

    public SystemException(SystemCode code, String message) {
        this(code, message, null);
    }

    public SystemException(SystemCode code, String message, Object[] args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public SystemCode getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
