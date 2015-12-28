package com.baidu.oped.sia.boot.client.http;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class InternalError {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InternalError that = (InternalError) o;

        return Objects.equal(code, that.code) &&
                Objects.equal(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code, message);
    }
}
