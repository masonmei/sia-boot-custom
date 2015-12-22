package com.baidu.oped.sia.boot.access.source;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Ip Limitation, if specified, only the client from the given ip scopes can visit the endpoint.
 *
 * @author mason
 */
public class Limit {

    private String[] paths = new String[0];
    private RequestMethod[] methods = new RequestMethod[0];
    private List<ClientRange> clientRanges = new ArrayList<>();



}
