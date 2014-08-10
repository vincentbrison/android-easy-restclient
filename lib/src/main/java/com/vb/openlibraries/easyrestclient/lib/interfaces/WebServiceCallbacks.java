package com.vb.openlibraries.easyrestclient.lib.interfaces;

import com.vb.openlibraries.easyrestclient.lib.Response;
import com.vb.openlibraries.easyrestclient.lib.WebService;

/**
 * Created by Vincent Brison on 10/08/2014.
 */
public interface WebServiceCallbacks {
    public void onWebServiceFinishWithSuccess(WebService ws, Response r);
    public void onWebServiceFinishWithException(WebService ws, Exception e);
}
