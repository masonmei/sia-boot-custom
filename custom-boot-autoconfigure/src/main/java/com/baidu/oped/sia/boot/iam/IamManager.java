package com.baidu.oped.sia.boot.iam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mason on 12/3/15.
 */
public interface IamManager {
    void checkUserAuth(String userId);

    void checkServiceAuth(String scope);

    void checkServiceAndUserAuth(String scope, String userId);

    String getUserFromIam(HttpServletRequest servletRequest);

    boolean isActive();
}
