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
