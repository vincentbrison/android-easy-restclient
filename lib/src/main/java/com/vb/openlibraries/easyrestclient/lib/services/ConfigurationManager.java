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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Add a class header comment!
 */
public class ConfigurationManager {
    private static Map<Class<? extends WebServiceConfiguration>, WebServiceConfiguration> mConfigurations = new HashMap<Class<? extends WebServiceConfiguration>, WebServiceConfiguration>();

    protected static WebServiceConfiguration getConfiguration(Class<? extends WebServiceConfiguration> config) {
        if (!mConfigurations.containsKey(config)) {
            try {
                final Constructor<? extends WebServiceConfiguration> ctorConf = config
                        .getConstructor();
                mConfigurations.put(config, ctorConf.newInstance());
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return mConfigurations.get(config);
    }
}
