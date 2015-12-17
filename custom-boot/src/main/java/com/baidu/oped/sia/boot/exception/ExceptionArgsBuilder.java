package com.baidu.oped.sia.boot.exception;

import static java.lang.String.format;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Exception Arguments builder.
 *
 * @author mason
 */
public class ExceptionArgsBuilder {
    private List<Object> params = new LinkedList<>();

    private ExceptionArgsBuilder() {
    }

    /**
     * Construct a ArgsBuilder instance.
     *
     * @return an absolutely new ExceptionArgsBuilder
     */
    public static ExceptionArgsBuilder get() {
        return new ExceptionArgsBuilder();
    }

    /**
     * join the params with a and syntax.
     *
     * @param params the param to be join together
     * @param <T>    the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder and(T... params) {
        this.params.add(join("and", params));
        return this;
    }

    /**
     * join the params with a and syntax.
     *
     * @param params the param to be join together
     * @param <T>    the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder and(Collection<T> params) {
        this.params.add(join("and", params));
        return this;
    }

    /**
     * Build arguments.
     *
     * @return the arguments object array
     */
    public Object[] args() {
        Object[] args = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            args[i] = params.get(i);
        }
        return args;
    }

    /**
     * join the params with an or syntax.
     *
     * @param params the param to be join together
     * @param <T>    the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder or(Collection<T> params) {
        this.params.add(join("or", params));
        return this;
    }

    /**
     * join the params with an or syntax.
     *
     * @param params the param to be join together
     * @param <T>    the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder or(T... params) {
        this.params.add(join("or", params));
        return this;
    }

    /**
     * Build an range param.
     *
     * @param from the region begin.
     * @param to   the region end
     * @param <T>  the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder range(T from, T to) {
        String fromString = "(-∞";
        String toString = "+∞)";

        if (from != null) {
            fromString = "[" + from.toString();
        }
        if (to != null) {
            toString = to.toString() + "]";
        }
        this.params.add(String.format("%s, %s", fromString, toString));
        return this;
    }

    /**
     * Add the params to arguments.
     *
     * @param params the param to be join together
     * @param <T>    the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder with(T... params) {
        for (T param : params) {
            this.params.add(param);
        }
        return this;
    }

    /**
     * Add the params to arguments.
     *
     * @param params the param to be join together
     * @param <T>    the Param type
     *
     * @return the instance with new param added
     */
    public <T> ExceptionArgsBuilder with(List<T> params) {
        for (T param : params) {
            this.params.add(param);
        }
        return this;
    }

    /**
     * Join the params with given decimeter.
     *
     * @param decimeter the decimeter.
     * @param params    the params to join together
     * @param <T>       the param type
     *
     * @return the joined result
     */
    protected <T> String join(String decimeter, T... params) {
        if (params == null || params.length == 0) {
            return "";
        } else {
            if (params.length == 1) {
                return params[0].toString();
            } else {
                String result = StringUtils.arrayToDelimitedString(params, ", ");
                int lastIndex = result.lastIndexOf(", ");
                return format("%s %s %s", result.substring(0, lastIndex), decimeter, result.substring(lastIndex + 2));
            }
        }
    }

    /**
     * Join the params with given decimeter.
     *
     * @param decimeter the decimeter.
     * @param params    the params to join together
     * @param <T>       the param type
     *
     * @return the joined result
     */
    protected <T> String join(String decimeter, Collection<T> params) {
        if (params == null || params.size() == 0) {
            return "";
        } else {
            if (params.size() == 1) {
                return params.iterator().next().toString();
            } else {
                String result = StringUtils.collectionToDelimitedString(params, ", ");
                int lastIndex = result.lastIndexOf(", ");
                return format("%s %s %s", result.substring(0, lastIndex), decimeter, result.substring(lastIndex + 2));
            }
        }
    }
}
