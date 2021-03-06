package com.baidu.oped.sia.boot.decode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServlet Request Uri decode support.
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

    /**
     * Get the Wrapped request uri that decoded.
     *
     * @return decoded uri
     */
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
