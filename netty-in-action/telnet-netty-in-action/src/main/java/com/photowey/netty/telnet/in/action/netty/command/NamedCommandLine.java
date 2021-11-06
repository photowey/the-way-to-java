package com.photowey.netty.telnet.in.action.netty.command;

import com.google.common.collect.Lists;
import com.photowey.netty.telnet.in.action.exception.NamedException;
import com.photowey.netty.telnet.in.action.util.StringUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.TypeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code NamedCommandLine}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedCommandLine {

    private CommandLine commandLine;

    public NamedCommandLine() {
    }

    public NamedCommandLine(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public boolean hasOption(String opt) {
        return this.commandLine.hasOption(opt);
    }

    public List<Option> getOptions() throws NamedException {
        Option[] options = this.commandLine.getOptions();
        return Lists.newArrayList(options);
    }

    public <T> List<T> getOptions(String opt, List<T> value) throws NamedException {
        List<T> options = this.getOption(opt);
        return null == options || options.isEmpty() ? value : options;
    }

    public <T> List<T> getOptions(String opt) throws NamedException {
        String[] results = this.commandLine.getOptionValues(opt);
        Option option = this.resolveOption(opt);
        if (null == option || null == results) {
            return new ArrayList<>();
        }

        Class<?> clazz = (Class<?>) option.getType();
        List<T> findOptions = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            T val = this.getOption(results[i], clazz);
            findOptions.add(val);
        }

        return findOptions;
    }

    public <T> T getOption(String opt, T value) throws NamedException {
        T option = this.getOption(opt);

        return null == option ? value : option;
    }

    public <T> T getOption(String opt) throws NamedException {
        List<T> options = this.getOptions(opt);
        if (options.isEmpty()) {
            return null;
        }

        return options.get(0);
    }

    private <T> T getOption(String res, Class<?> clazz) throws NamedException {
        try {
            return (T) TypeHandler.createValue(res, clazz);
        } catch (ParseException e) {
            throw new NamedException(e);
        }

    }

    public List<String> getArgList() {
        return this.commandLine.getArgList();
    }

    private Option resolveOption(String opt) {
        String optInner = StringUtils.stripPrefixIfNecessary(opt);
        for (Option option : this.commandLine.getOptions()) {
            if (optInner.equals(option.getOpt())) {
                return option;
            }

            if (optInner.equals(option.getLongOpt())) {
                return option;
            }
        }

        return null;
    }

    public static NamedCommandLineBuilder builder() {
        return new NamedCommandLineBuilder();
    }

    public static class NamedCommandLineBuilder {
        private CommandLine commandLine;

        NamedCommandLineBuilder() {
        }

        public NamedCommandLineBuilder commandLine(CommandLine commandLine) {
            this.commandLine = commandLine;
            return this;
        }

        public NamedCommandLine build() {
            return new NamedCommandLine(this.commandLine);
        }

        @Override
        public String toString() {
            return "NamedCommandLine.NamedCommandLineBuilder(commandLine=" + this.commandLine + ")";
        }
    }
}
