package com.baidu.oped.sia.boot.exception;

import static com.baidu.oped.sia.boot.exception.ExceptionKeyProvider.PARAM_VALUE_NOT_OF_SCOPE;

/**
 * Parameter Exception when param value is not in the given scope.
 *
 * @author mason
 */
public class ParamValueNotInScopeException extends ParameterValueException {

    public ParamValueNotInScopeException(Object[] args) {
        super(PARAM_VALUE_NOT_OF_SCOPE, args);
    }
}
