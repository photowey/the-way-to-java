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
package com.photowey.drools.in.action.domain.helloworld;

import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;

/**
 * {@code MessageTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/09
 */
class MessageTest {

    @Test
    void testHelloWorld() {
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();

        execute(ks, kc);
    }

    public static void execute(KieServices ks, KieContainer kc) {
        KieSession ksession = kc.newKieSession(
            "io.github.photowey.drools.kbase.ksession.helloworld"
        );

        ksession.setGlobal("list", new ArrayList<>());

        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.addEventListener(new DebugRuleRuntimeEventListener());

        KieRuntimeLogger logger = ks.getLoggers().newFileLogger(ksession, "logs/helloworld");

        final Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);

        ksession.insert(message);
        ksession.fireAllRules();

        logger.close();

        ksession.dispose();
    }
}
