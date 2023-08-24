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
package com.photowey.jvm.in.action.oom;

/**
 * {@code MetaspaceOOM}
 *
 * <pre>
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 * </pre>
 *
 * @author photowey
 * @date 2022/10/07
 * @since 1.0.0
 */
public class MetaspaceOOM extends ClassLoader {

    public static void main(String[] args) {
        /*int j = 0;
        try {
            MetaspaceOOM test = new MetaspaceOOM();
            for (int i = 0; i < 1_000_000; i++) {
                ClassWriter classWriter = new ClassWriter(0);
                classWriter.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, "Class" + i, null, "java/lang/Object", null);
                byte[] code = classWriter.toByteArray();
                test.defineClass("Class" + i, code, 0, code.length);
                j++;
            }
        } finally {
            System.out.println(j);
        }*/
    }
}
