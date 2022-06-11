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
package com.photowey.netty.telnet.in.action.netty.command;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;
import com.photowey.netty.telnet.in.action.pool.NamedThreadPool;
import com.photowey.netty.telnet.in.action.pool.registry.NamedThreadPoolRegistry;
import com.photowey.netty.telnet.in.action.util.StringUtils;

/**
 * {@code NamedCommandHandler}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedCommandHandler {

    TelnetCommandHandler commandHandler = new TelnetCommandHandler();

    static {
        init();
    }

    public NamedCommandHandler() {
    }

    private static void init() {
        NamedThreadPool namedThreadPool = populateCommandThreadPool();
        NamedThreadPoolRegistry.registerThreadPool(NamedConstants.TELNET_COMMAND_THREAD_POOL_NAME, namedThreadPool);
    }

    private static NamedThreadPool populateCommandThreadPool() {
        NamedThreadPool namedThreadPool = new NamedThreadPool()
                .setAllowCoreThreadTimeOut(true)
                .setThreadPoolName(NamedConstants.TELNET_COMMAND_THREAD_POOL_NAME)
                .setDaemon(true);

        return namedThreadPool;
    }

    // ====================================================================== request

    public String handleRequest(String cmd) {

        String commandResult = this.handleCommand(cmd);

        commandResult = commandResult.replace("\n", NamedConstants.TELNET_STRING_END);
        if (StringUtils.isEmpty(commandResult)) {
            commandResult = NamedConstants.TELNET_STRING_END;
        } else if (!commandResult.endsWith(NamedConstants.TELNET_STRING_END)) {
            commandResult = commandResult + NamedConstants.TELNET_STRING_END + NamedConstants.TELNET_STRING_END;
        } else if (!commandResult.endsWith(NamedConstants.TELNET_STRING_END.concat(NamedConstants.TELNET_STRING_END))) {
            commandResult = commandResult + NamedConstants.TELNET_STRING_END;
        }

        commandResult = commandResult + this.handlePrompt();

        return commandResult;
    }

    // ====================================================================== command

    public String handleCommand(String cmd) {
        if (StringUtils.isEmpty(cmd)) {
            return NamedConstants.EMPTY_STRING;
        }
        Command command = new NamedCommand(cmd);
        if (this.commandHandler.validate(command)) {
            return this.commandHandler.handleCommand(command);
        }

        return this.handleHelp(cmd);
    }

    // ====================================================================== help

    public String handleHelp(String command) {
        String help = new HelpCommand(command).execute();
        StringBuilder helpBuilder = new StringBuilder();

        return helpBuilder.append(String.format("The cmd:[%s] error,read the help please!", command)).append("\n").append(help).toString();
    }

    // ====================================================================== prompt

    public String handlePrompt() {
        return NamedConstants.TELNET_SESSION_PROMPT;
    }

}