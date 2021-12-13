/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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

/**
 * {@code Premain}
 *
 * @author photowey
 * @date 2021/12/13
 * @since 1.0.0
 */
public class Premain extends AbstractAgent {

    private static final String HELLO_TARGET = "com.photowey.java.agent.in.action.target.hello.HelloTarget";
    private static final String HELLO_TARGET_PATH = HELLO_TARGET.replaceAll("\\.", "/");

    public static void premain(String agentOps, Instrumentation inst) {
        try {
            String helloTargetClass = Premain.class.getClassLoader().getResource("").toURI().getPath() + "lib" + File.separator + "HelloTarget.class";
            helloTargetClass = helloTargetClass.replaceAll("^/*", "");
            doTransform(agentOps, inst, HELLO_TARGET_PATH, helloTargetClass);
        } catch (Exception e) {
        }
    }
}
