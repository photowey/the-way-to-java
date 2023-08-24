/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.translator.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code TranslatorHttpProperties}
 *
 * @author photowey
 * @date 2022/10/15
 * @since 1.0.0
 */
public class TranslatorHttpProperties implements Serializable {

    private static final long serialVersionUID = -5453604230161791240L;

    private Translator translator = new Translator();
    private HttpClient httpClient = new HttpClient();
    private OkHttp okHttp = new OkHttp();

    public static class Translator implements Serializable {
        private String api = "https://api.fanyi.baidu.com/api/trans/vip/translate";

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }
    }

    /**
     * {@code ApacheHttpClient}
     */
    public static class HttpClient implements Serializable {

        private static final long serialVersionUID = 7282662461850242912L;

        private boolean enabled = false;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getWriteTimeout() {
            return writeTimeout;
        }

        public void setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
        }

        public int getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(int maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
        }

        public long getKeepAliveDuration() {
            return keepAliveDuration;
        }

        public void setKeepAliveDuration(long keepAliveDuration) {
            this.keepAliveDuration = keepAliveDuration;
        }
    }

    public static class OkHttp implements Serializable {

        private static final long serialVersionUID = 474081402674142730L;

        private boolean enabled = true;
        private int connectTimeout = 5;
        private int readTimeout = 30;
        private int writeTimeout = 30;
        private int maxIdleConnections = 200;
        private long keepAliveDuration = 300L;
        private List<String> ignoreApis = new ArrayList<>();

        public OkHttp() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getWriteTimeout() {
            return writeTimeout;
        }

        public void setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
        }

        public int getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(int maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
        }

        public long getKeepAliveDuration() {
            return keepAliveDuration;
        }

        public void setKeepAliveDuration(long keepAliveDuration) {
            this.keepAliveDuration = keepAliveDuration;
        }

        public List<String> getIgnoreApis() {
            return ignoreApis;
        }

        public void setIgnoreApis(List<String> ignoreApis) {
            this.ignoreApis = ignoreApis;
        }
    }

    public Translator getTranslator() {
        return translator;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }


    public OkHttp getOkHttp() {
        return okHttp;
    }
}
