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
package com.photowey.redis.in.action.proxy.template;

import com.photowey.common.in.action.func.lambda.LambdaFunction;

import java.util.concurrent.TimeUnit;

/**
 * {@code ICacheTemplate}
 *
 * @author photowey
 * @date 2023/12/28
 * @since 1.0.0
 */
public interface ICacheTemplate {

    boolean exists(final String key);

    void delete(final String key);

    <T> void set(final String key, T value);

    <T> void reset(final String key, T value);

    <T> void set(final String key, T value, long expires, TimeUnit timeUnit);

    <T> void reset(final String key, T value, long expires, TimeUnit timeUnit);

    <T> T get(final String key);

    void setString(final String key, String value);

    void resetString(final String key, String value);

    void setString(final String key, String value, long expires, TimeUnit timeUnit);

    void resetString(final String key, String value, long expires, TimeUnit timeUnit);

    String getString(final String key);

    // ---------------------------------------------------------------- incr

    Long incr(String key);

    Long incr(String key, Long delta);

    Long hashIncr(String key, String filed);


    Long hashIncr(String key, String filed, Long delta);

    <T> Long hashIncr(String key, LambdaFunction<T, ?> filed);

    <T> Long hashIncr(String key, LambdaFunction<T, ?> filed, Long delta);

    // ---------------------------------------------------------------- decr

    Long decr(String key);

    Long decr(String key, Long delta);

    Long hashDecr(String key, String filed);

    Long hashDecr(String key, String filed, Long delta);

    <T> Long hashDecr(String key, LambdaFunction<T, ?> filed);

    <T> Long hashDecr(String key, LambdaFunction<T, ?> filed, Long delta);
}
