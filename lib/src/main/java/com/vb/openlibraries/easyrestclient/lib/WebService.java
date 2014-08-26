package com.vb.openlibraries.easyrestclient.lib;

import java.util.Map;

/**
 * Created by Vincent Brison on 10/08/2014.
 */
public abstract class WebService {
    public enum METHOD_NAME{GET, POST, PUT, DELETE}
    public enum SCHEME{HTTP, HTTPS}

    public abstract String getBaseURL();
    public abstract String getPathURL();
    public abstract METHOD_NAME getMethodName();
    public abstract SCHEME getProtocol();

    protected Map<String, String> args;

}
