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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Response;
import com.vb.openlibraries.easyrestclient.lib.interfaces.WebServiceCallbacks;

import java.io.IOException;

/**
 * TODO: Add a class header comment!
 */
public abstract class GetJSONWebService<T> extends GetWebService {

    private static ObjectMapper sMapper;

    protected T mResult;

    public T getResult() {
        return mResult;
    }

    public abstract Class<T> getResultClass();

    @Override
    public void onNetworkTaskStartAsyncWork(NetworkTask caller) {
        super.onNetworkTaskStartAsyncWork(caller);
        initMapper();
    }

    @Override
    public void onNetworkTaskFinishAsyncWork(NetworkTask caller, Response response) {
        super.onNetworkTaskFinishAsyncWork(caller, response);
        try {
            mResult = sMapper.readValue(response.body().string(), getResultClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkTaskFinishWithSuccess(NetworkTask caller, Response response) {
        super.onNetworkTaskFinishWithSuccess(caller, response);
        for (WebServiceCallbacks callback : mWSCallbacks) {
            if (callback != null) {
                callback.onWebServiceFinishWithSuccess(this);
            }
        }
    }

    private static void initMapper() {
        if (sMapper == null) {
            sMapper = new ObjectMapper();
            sMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            sMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        }
    }
}
