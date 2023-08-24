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
package com.photowey.design.pattern.in.action.single;

/**
 * {@code Singleton}
 *
 * @author photowey
 * @date 2022/03/05
 * @since 1.0.0
 */
public class Singleton {

    /**
     * 常规的
     */
    public static class NormalSingleton {

        private static final NormalSingleton INSTANCE = new NormalSingleton();

        private NormalSingleton() {
        }

        public static NormalSingleton getInstance() {
            return INSTANCE;
        }
    }

    /**
     * 糟糕的
     */
    public static class BadSingleton {

        private static BadSingleton INSTANCE;

        private BadSingleton() {
        }

        public static BadSingleton getInstance() {
            if (null == INSTANCE) {
                INSTANCE = new BadSingleton();
            }

            return INSTANCE;
        }
    }

    /**
     * 双端检查的
     */
    public static class DoubleCheckSingleton {

        private static volatile DoubleCheckSingleton INSTANCE;

        private DoubleCheckSingleton() {
        }

        public static DoubleCheckSingleton getInstance() {
            if (null == INSTANCE) {
                synchronized (DoubleCheckSingleton.class) {
                    if (null == INSTANCE) {
                        INSTANCE = new DoubleCheckSingleton();
                    }
                }
            }

            return INSTANCE;
        }
    }

    /**
     * 静态内部类
     */
    public static class StaticInnerClassSingleton {

        /**
         * 防止反射
         */
        private StaticInnerClassSingleton() {
            if (null != StaticInnerClassSingletonFactory.INSTANCE) {
                throw new IllegalStateException("wrong way to access");
            }
        }

        public static class StaticInnerClassSingletonFactory {
            private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
        }

        public static StaticInnerClassSingleton getInstance() {
            return StaticInnerClassSingletonFactory.INSTANCE;
        }

    }

    /**
     * 枚举
     */
    public enum EnumSingleton {

        INSTANCE;

        public void sayHello() {
            System.out.println("Hello World!");
        }

    }

}
