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

import com.squareup.okhttp.Response;
import com.vb.openlibraries.easyrestclient.lib.interfaces.NetworkTaskCallbacks;
import com.vb.openlibraries.easyrestclient.lib.interfaces.WebServiceCallbacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * TODO: Add some description.
 */
public abstract class WebService implements NetworkTaskCallbacks {

    private static int DEFAULT_NUMBER_OF_CONCURRENT_THREADS = 5;
    private static Executor sExecutor = Executors.newFixedThreadPool(DEFAULT_NUMBER_OF_CONCURRENT_THREADS);

    protected Map<String, String> mArgs;
    protected Map<String, String> mHeaders;
    protected List<WebServiceCallbacks> mWSCallbacks;

    protected abstract Class<? extends WebServiceConfiguration> getWebServiceConfiguration();

    public void execute() {
        NetworkTask networkTask = new NetworkTask(this, this);
        networkTask.executeOnExecutor(sExecutor);
    }

    public WebService() {
        mArgs = new HashMap<String, String>();
        mHeaders = new HashMap<String, String>();
        mWSCallbacks = new ArrayList<WebServiceCallbacks>();
    }

    public void addArgument(String key, String value) {
        mArgs.put(key, value);
    }

    public void addCallback(WebServiceCallbacks callback) {
        mWSCallbacks.add(callback);
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getArgs() {
        return mArgs;
    }

    @Override
    public void onNetworkTaskStartAsyncWork(NetworkTask caller) {
    }

    @Override
    public void onNetworkTaskFinishAsyncWork(NetworkTask caller, Response response) {
    }

    @Override
    public void onNetworkTaskFinishWithSuccess(NetworkTask caller, Response response) {
    }

    @Override
    public void onNetworkTaskFinishWithException(NetworkTask caller, Exception exception) {
    }
}
