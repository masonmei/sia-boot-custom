package com.baidu.oped.sia.boot.decode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

/**
 * HttpServlet Request Uri decode support.
 * <p>
 *
 * @author mason
 */
public class DecodeUriWrapper extends HttpServletRequestWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(DecodeUriWrapper.class);

    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the request to be wrapped
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
            LOG.warn("meet unsupported encoding");
            return super.getRequestURI();
        }
    }

}
