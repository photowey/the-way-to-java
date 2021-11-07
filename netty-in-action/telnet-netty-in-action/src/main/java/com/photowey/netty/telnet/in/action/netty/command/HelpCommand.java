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
package com.photowey.netty.telnet.in.action.netty.command;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;

/**
 * {@code HelpCommand}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class HelpCommand extends AbstractCommandAdaptor {

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public boolean validate() {
        String[] syntax = this.command.trim().split(NamedConstants.SPACE_SEPARATOR);
        return "named".equals(syntax[0]);
    }

    @Override
    protected String doExecute(NamedCommandLine commandLine) throws Exception {
        return HELP_MESSAGE;
    }

    private static final String HELP_MESSAGE = "Named Command Tips:\n"
            + "  USAGE: named [option...] [arguments...]\n"
            + "  -h,--help:  Shows the help message.\n"
            + "  -a:  Shows all info.\n";
}
