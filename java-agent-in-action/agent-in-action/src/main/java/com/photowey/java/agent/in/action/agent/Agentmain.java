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
package com.photowey.java.agent.in.action.agent;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * {@code Agentmain}
 *
 * @author photowey
 * @date 2021/12/13
 * @since 1.0.0
 */
public class Agentmain extends AbstractAgent {

    private static final String HELLO_TARGET = "com.photowey.java.agent.in.action.target.hello.HelloTarget";
    private static final String HELLO_TARGET_PATH = HELLO_TARGET.replaceAll("\\.", "/");

    public static void agentmain(String agentOps, Instrumentation inst) {
        try {
            String helloTargetClass = Premain.class.getClassLoader().getResource("").toURI().getPath() + File.separator + "lib" + File.separator + "HelloTarget.class";
            helloTargetClass = helloTargetClass.replaceAll("^/*", "");
            doTransform(agentOps, inst, HELLO_TARGET_PATH, helloTargetClass);
            for (Class loadedClass : inst.getAllLoadedClasses()) {
                if (loadedClass.getName().contains(HELLO_TARGET)) {
                    try {
                        inst.retransformClasses(loadedClass);
                    } catch (UnmodifiableClassException e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }

}
