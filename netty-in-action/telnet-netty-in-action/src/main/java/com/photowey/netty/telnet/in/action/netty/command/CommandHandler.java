package com.photowey.netty.telnet.in.action.netty.command;

/**
 * {@code CommandHandler}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public interface CommandHandler {

    boolean validate(Command command);

    String handleCommand(Command command);

}
