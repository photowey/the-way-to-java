/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.commom.in.action.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * {@code FutureGetter}
 *
 * @author photowey
 * @date 2022/11/28
 * @since 1.0.0
 */
public class FutureGetter {

    private FutureGetter() {
        // utility class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static <T> T quietGet(Future<T> future) {
        return get(future, (e) -> {
        });
    }

    public static <T> T get(Future<T> future, Consumer<Exception> fx) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fx.accept(e);
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            fx.accept(e);
            throw new RuntimeException(e);
        }
    }
}
