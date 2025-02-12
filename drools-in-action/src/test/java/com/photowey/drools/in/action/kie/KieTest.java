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
package com.photowey.drools.in.action.kie;

import com.photowey.drools.in.action.App;
import com.photowey.drools.in.action.core.domain.helloworld.Message;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * {@code KieTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/11
 */
@SpringBootTest(classes = {App.class})
class KieTest {

    @Autowired
    private KieFileSystem kieFileSystem;

    @Autowired
    private KieContainer kieContainer;
    @Autowired
    private KieBase kieBase;
    @Autowired
    private KieSession kieSession;

    @Test
    void testKie() {
        KieServices ks = KieServices.get();

        kieSession.setGlobal("list", new ArrayList<>());

        kieSession.addEventListener(new DebugAgendaEventListener());
        kieSession.addEventListener(new DebugRuleRuntimeEventListener());

        KieRuntimeLogger logger = ks.getLoggers().newFileLogger(kieSession, "logs/helloworld");

        final Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);

        kieSession.insert(message);
        kieSession.fireAllRules((agendaFilter) -> {
            return agendaFilter.getRule().getName().toLowerCase().contains("helloworld");
        });

        logger.close();

        kieSession.dispose();
    }
}
