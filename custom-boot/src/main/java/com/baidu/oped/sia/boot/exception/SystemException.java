package com.baidu.oped.sia.boot.exception;

import java.util.Arrays;

/**
 * Base Exception of all the runtime Exceptions.
 *
 * @author meidongxu@baidu.com
 */
public abstract class SystemException extends RuntimeException {

    private final SystemCode code;
    private final Object[] args;

    public SystemException(SystemCode code, String message) {
        this(code, message, new Object[0]);
    }

    /**
     * Construct an system exception instance.
     *
     * @param code    the exception code
     * @param message the exception msg
     * @param args    the argument for customizer msg
     */
    public SystemException(SystemCode code, String message, Object[] args) {
        super(message);
        this.code = code;
        if (args == null) {
            args = new Object[0];
        }
        this.args = Arrays.copyOf(args, args.length);
    }

    public Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    public SystemCode getCode() {
        return code;
    }
}
