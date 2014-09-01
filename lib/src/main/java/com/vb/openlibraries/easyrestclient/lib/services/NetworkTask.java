/*
 * Copyright 2014 Vincent Brison.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vb.openlibraries.easyrestclient.lib.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vb.openlibraries.easyrestclient.lib.interfaces.NetworkTaskCallbacks;
import com.vb.openlibraries.easyrestclient.lib.threads.WSAsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO: Add description.
 */
public class NetworkTask extends WSAsyncTask {

    private static int DEFAULT_MAX_TRIES_ALLOWEDED = 4;
    private final static OkHttpClient client = new OkHttpClient();
    private WebService mWebService;
    private List<NetworkTaskCallbacks> mCallbacks;
    private int maxTriesAllowed = DEFAULT_MAX_TRIES_ALLOWEDED;

    public NetworkTask() {
        mCallbacks = new ArrayList<NetworkTaskCallbacks>();
    }

    public NetworkTask(WebService ws, NetworkTaskCallbacks callback) {
        mWebService = ws;
        mCallbacks = new ArrayList<NetworkTaskCallbacks>();
        mCallbacks.add(callback);
    }

    public NetworkTask(WebService ws, List<NetworkTaskCallbacks> callbacks) {
        mWebService = ws;
        mCallbacks = callbacks;
    }

    public void setWebService(WebService webService) {
        mWebService = webService;
    }

    public void addCallback(NetworkTaskCallbacks callback) {
        mCallbacks.add(callback);
    }

    public void removeCallback(NetworkTaskCallbacks callback) {
        mCallbacks.remove(callback);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Request request = prepareNetworkTask(mWebService);

        // Call callbacks in this thread to perform heavy task not on the UI thread.
        for (NetworkTaskCallbacks callback : mCallbacks) {
            if (callback != null) {
                callback.onNetworkTaskStartAsyncWork(this);
            }
        }

        // Do the network call.
        Response response = null;
        int numberOfTries = 0;
        while (response == null
                || (!response.isSuccessful() && numberOfTries < maxTriesAllowed)) {
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();

                // If all the tries has been consumed, return to service with exception.
                if (numberOfTries == maxTriesAllowed - 1) {
                    return e;
                }
            }
        }

        // Call callbacks in this thread to perform heavy task not on the UI thread.
        for (NetworkTaskCallbacks callback : mCallbacks) {
            if (callback != null) {
                callback.onNetworkTaskFinishAsyncWork(this, response);
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (result instanceof Exception) {
            // Call callbacks with the exception.
            for (NetworkTaskCallbacks callback : mCallbacks) {
                if (callback != null) {
                    callback.onNetworkTaskFinishWithException(this, (Exception) result);
                }
            }
        } else {
            // Call callbacks with the result.
            for (NetworkTaskCallbacks callback : mCallbacks) {
                if (callback != null) {
                    callback.onNetworkTaskFinishWithSuccess(this, (Response) result);
                }
            }
        }
    }

    private Request prepareNetworkTask(WebService webservice) {
        Request.Builder requestBuilder = new Request.Builder();
        WebServiceConfiguration configuration = ConfigurationManager.getConfiguration(mWebService.getWebServiceConfiguration());

        // Add headers
        for (Map.Entry<String, String> entry : mWebService.getHeaders().entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        // Init url.
        StringBuffer urlBuilder = new StringBuffer();
        urlBuilder.append(configuration.getProtocol())
                .append("://")
                .append(configuration.getBaseURL())
                .append(configuration.getPathURL());

        // Add arguments
        if (mWebService instanceof GetWebService) {
            requestBuilder.get();
            urlBuilder.append('?');
            boolean firstArgAdded = false;
            for (Map.Entry<String, String> entry : mWebService.getArgs().entrySet()) {
                try {
                    if (firstArgAdded) {
                        urlBuilder.append('&');
                    } else {
                        firstArgAdded = true;
                    }
                    urlBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                            .append('=')
                            .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        // Set the url.
        requestBuilder.url(urlBuilder.toString());

        return requestBuilder.build();
    }
}
