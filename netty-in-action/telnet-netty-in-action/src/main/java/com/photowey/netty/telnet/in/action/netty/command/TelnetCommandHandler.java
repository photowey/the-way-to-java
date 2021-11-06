package com.photowey.netty.telnet.in.action.netty.command;

/**
 * {@code TelnetCommandHandler}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class TelnetCommandHandler implements CommandHandler {

    @Override
    public boolean validate(Command command) {
        return command.validate();
    }

    @Override
    public String handleCommand(Command command) {
        return command.execute();
    }
}
