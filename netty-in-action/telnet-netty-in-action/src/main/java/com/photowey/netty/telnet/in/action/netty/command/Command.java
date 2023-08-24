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
package com.photowey.netty.telnet.in.action.netty.command;

import org.apache.commons.cli.Option;

/**
 * {@code Command}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface Command {

    String name();

    boolean validate();

    void addOption(Option option);

    String execute(String[] args);

    default String execute() {
        return "";
    }

    void write(String message, Object... params) throws Exception;

    void writeLine(String message, Object... params) throws Exception;

    void writeError(String message, Object... params) throws Exception;
}
