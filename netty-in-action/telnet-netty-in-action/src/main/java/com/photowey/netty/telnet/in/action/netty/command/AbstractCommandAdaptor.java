package com.photowey.netty.telnet.in.action.netty.command;

import com.photowey.netty.telnet.in.action.constant.NamedConstants;
import com.photowey.netty.telnet.in.action.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * {@code AbstractCommandAdaptor}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractCommandAdaptor implements Command {

    protected static final String HELP_KEY = "help";
    private static final Option HELP = Option.builder("h").longOpt("help").required(false).hasArg(false).desc("Shows the help message.").build();
    private static final Option ALL = Option.builder("a").longOpt("all").required(false).hasArg(false).desc("Shows all xxx.").build();

    protected Options options = new Options();
    protected String command;

    protected AbstractCommandAdaptor() {
        this.addOption(HELP);
        this.addOption(ALL);
    }

    @Override
    public void addOption(Option option) {
        this.options.addOption(option);
    }

    @Override
    public String execute() {
        String[] syntax = this.command.trim().split(NamedConstants.SPACE_SEPARATOR);
        return this.execute(syntax);
    }

    @Override
    public String execute(String[] args) {
        String response = "";
        try {
            CommandLine commandLine = new DefaultParser().parse(this.options, args);
            NamedCommandLine command = NamedCommandLine.builder().commandLine(commandLine).build();
            if (commandLine.hasOption(HELP_KEY)) {
                return new HelpCommand(this.command).doExecute(command);
            } else {
                response = this.doExecute(command);
            }
        } catch (Exception e) {
            if (StringUtils.isNotEmpty(e.getMessage())) {
                response = e.getMessage();
                log.error("handle the command:[{}] exception", this.command, e);
            }
        }

        return response;
    }

    @Override
    public void write(String message, Object... params) throws Exception {
    }

    @Override
    public void writeLine(String message, Object... params) throws Exception {
    }

    @Override
    public void writeError(String message, Object... params) throws Exception {
    }

    protected abstract String doExecute(NamedCommandLine commandLine) throws Exception;
}
