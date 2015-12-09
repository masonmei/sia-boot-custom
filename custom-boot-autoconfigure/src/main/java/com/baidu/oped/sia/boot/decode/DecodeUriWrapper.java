package com.baidu.oped.sia.boot.decode;

import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

/**
 * HttpServlet Request Uri decode support
 * <p/>
 * Created by mason on 11/23/15.
 */
public class DecodeUriWrapper extends HttpServletRequestWrapper {

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public DecodeUriWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getRequestURI() {
        try {
            return UriUtils.decode(super.getRequestURI(), DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return super.getRequestURI();
        }
    }

}
