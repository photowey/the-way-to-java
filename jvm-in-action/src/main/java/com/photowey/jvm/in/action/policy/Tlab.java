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
package com.photowey.jvm.in.action.policy;

/**
 * {@code Tlab}
 * UseTLAB: 默认开启
 *
 * @author photowey
 * @date 2022/10/06
 * @since 1.0.0
 */
public class Tlab {

    /**
     * <pre>
     * ❯ jinfo -flag UseTLAB 7284
     * -XX:+UseTLAB
     * </pre>
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("running...");
        try {
            Thread.sleep(1_000_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
