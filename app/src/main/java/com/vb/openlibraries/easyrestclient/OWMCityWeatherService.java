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

package com.vb.openlibraries.easyrestclient;

import com.vb.openlibraries.easyrestclient.lib.interfaces.WebServiceCallbacks;
import com.vb.openlibraries.easyrestclient.lib.services.GetJSONWebService;
import com.vb.openlibraries.easyrestclient.lib.services.WebServiceConfiguration;
import com.vb.openlibraries.easyrestclient.omwcityweather.result.OWMCityWeatherResult;

/**
 * TODO: Add a class header comment!
 */
public class OWMCityWeatherService<T> extends GetJSONWebService<T> {

    public OWMCityWeatherService(WebServiceCallbacks callback, String city) {
        addArgument("q", city);
        addCallback(callback);
    }

    @Override
    protected Class<? extends WebServiceConfiguration> getWebServiceConfiguration() {
        return OWMWSConfiguration.class;
    }

    @Override
    public Class getResultClass() {
        return OWMCityWeatherResult.class;
    }
}
