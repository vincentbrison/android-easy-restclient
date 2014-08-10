package com.vb.openlibraries.easyrestclient.lib;

import java.util.Map;

/**
 * Created by Vincent Brison on 10/08/2014.
 */
public abstract class WebService {
    public enum METHOD_NAME{GET, POST}
    public enum PROTOCOL{HTTP, HTTPS}

    public abstract String getBaseURL();
    public abstract String getPathURL();
    public abstract METHOD_NAME getMethodName();
    public abstract PROTOCOL getProtocol();

    protected Map<String, String> args;

}
