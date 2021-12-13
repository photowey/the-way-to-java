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
package com.photowey.java.agent.in.action.spy;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * {@code Spy}
 *
 * @author photowey
 * @date 2021/12/13
 * @since 1.0.0
 */
@SpringBootApplication
public class Spy {

    public static void main(String[] args) {
        // new SpringApplicationBuilder(Spy.class).web(WebApplicationType.NONE).run(args);
        doAttach();
    }

    private static void doAttach() {
        // 查找所有 {@code JVM} 进程,排除 本工程 {@code JVM} 进程
        List<VirtualMachineDescriptor> attach = VirtualMachine.list()
                .stream().filter(jvm -> !(jvm.displayName().contains("Spy") || jvm.displayName().contains("org.jetbrains"))).collect(Collectors.toList());

        for (int i = 0; i < attach.size(); i++) {
            System.err.println("* [" + i + "] " + defaultNull(attach.get(i).displayName()) + ":" + attach.get(i).id());
        }
        System.err.println("请输入您需要 attach 的 JVM 进程索引编号:");
        Scanner scanner = new Scanner(System.in);
        String index = scanner.nextLine();
        VirtualMachineDescriptor virtualMachineDescriptor = attach.get(Integer.parseInt(index));
        try {
            VirtualMachine virtualMachine = VirtualMachine.attach(virtualMachineDescriptor.id());
            String agentJar = Spy.class.getClassLoader().getResource("").toURI().getPath() + "lib" + File.separator + "agent-in-action.jar";
            agentJar = agentJar.replaceAll("^/*", "");
            System.out.println("------------ agent jar path ------------");
            System.out.println(agentJar);
            System.out.println("------------ agent jar path ------------");
            virtualMachine.loadAgent(agentJar);
            virtualMachine.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String defaultNull(String source) {
        return (null == source || source.trim().length() == 0) ? "**" : source;
    }
}
