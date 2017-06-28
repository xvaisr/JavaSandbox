/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logging.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import logging.Logger;

/**
 *
 * @author Roman Vais
 */

public abstract class AbstractLogger implements Logger {

    public static final String DEFAULT_NAME = "Logger";
    public static final String DEFAULT_FORMAT = "#{name} [#{level}]: #{message} #{stack}";

    private static final String LOGGER_NAME = "name";
    private static final String LOGGING_LEVEL = "level";
    private static final String MESSAGE = "message";
    private static final String STACK = "stack";

    private static final int BUFFER = 000;


    private String name;
    private String format;
    private Logger.LogLevel level;
    private final StringBuilder msg;
    private final PrintWriter messageWriter;

    public AbstractLogger() {
        this(DEFAULT_FORMAT, DEFAULT_NAME, Logger.LogLevel.INFO);
    }

    public AbstractLogger(String name, String format, Logger.LogLevel lvl) {
        this.name = name;
        this.format = format;
        this.level = lvl;
        this.msg = new StringBuilder(BUFFER);
        this.messageWriter = new PrintWriter(new MessageWriter(this.msg));
    }

    @Override
    public final void setFormat(String f) {
        if (f != null && !f.isEmpty()) {
            this.format = f;
        }
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public final void setLoggingLevel(LogLevel lvl) {
        if (lvl != null) {
            this.level = lvl;
        }
    }

    @Override
    public final LogLevel getLoggingLevel() {
        return this.level;
    }

    private String privateFormatter(Logger.LogLevel level, String message, Throwable ex) {
        this.msg.setLength(0);

        for (int index = 0; index >= 0 && index < this.format.length();) {
            int match = this.format.indexOf('#', index);
            if (match >= 0) {
                this.msg.append(this.format.substring(index, match));
                if (this.format.charAt(match + 1) == '{') {
                    int i,j;
                    i = match + 1;
                    j = this.format.indexOf('}',i);
                    if (j < i) {
                        this.msg.append(" Error: Bad formating string ...");
                        break;
                    }

                    i++;
                    String joker = this.format.substring(i, j);
                    switch(joker) {
                        case LOGGER_NAME:
                            this.msg.append(this.name);
                        break;
                        case LOGGING_LEVEL:
                            this.msg.append(level.name());
                        break;
                        case MESSAGE:
                            this.msg.append(message);
                        break;
                        case STACK:
                            if(ex != null) {
                                this.msg.append('\n');
                                ex.printStackTrace(this.messageWriter);
                                this.msg.append('\n');
                            }
                        break;
                    }

                    match = j;
                }
                index = match + 1;
            }
            else {
                this.msg.append(format.substring(index));
                index = match;
            }
        }

        return this.msg.toString();
    }

    private String publicFormatter(String format, Object[] args) {
        return String.format(format, args);
    }

    private final void log(Logger.LogLevel level, String mesage, Throwable ex) {
        if ((this.level.getValue() >= level.getValue() && level.getValue() > 0) ||
            (this.level == level && level == LogLevel.SIMULATION))
        {
            this.write(this.privateFormatter(level, mesage, ex));
        }
    }

    private final void log(Logger.LogLevel level, String format, Object... arguments) {
        if ((this.level.getValue() >= level.getValue() && level.getValue() > 0) ||
            (this.level == level && level == LogLevel.SIMULATION))
        {
            this.write(this.publicFormatter(format, arguments));
        }
    }

    @Override
    public final void log(String mesage) {
        this.log(this.level, mesage, (Throwable) null);
    }

    @Override
    public final void log(String mesage, Throwable ex) {
        this.log(this.level, mesage, ex);
    }

    @Override
    public final void log(String format, Object... arguments) {
        this.log(this.level, format, arguments);
    }

    @Override
    public final void logDebug(String mesage) {
        this.logDebug(mesage, (Throwable) null);
    }

    @Override
    public final void logDebug(String mesage, Throwable ex) {
        this.log(LogLevel.DEBUG, mesage, ex);
    }

    @Override
    public final void logDebug(String format, Object... arguments) {

    }

    @Override
    public final void logInfo(String mesage) {
        this.logInfo(mesage, (Throwable) null);
    }

    @Override
    public final void logInfo(String mesage, Throwable ex) {
        this.log(LogLevel.INFO, mesage, ex);
    }

    @Override
    public final void logInfo(String format, Object... arguments) {

    }

    @Override
    public final void logWarning(String mesage) {
        this.logWarning(mesage, (Throwable) null);
    }

    @Override
    public final void logWarning(String mesage, Throwable ex) {
        this.log(LogLevel.WARNING, mesage, ex);
    }

    @Override
    public final void logWarning(String format, Object... arguments) {

    }

    @Override
    public final void logError(String mesage) {
        this.logError(mesage, (Throwable) null);
    }

    @Override
    public final void logError(String mesage, Throwable ex) {
        this.log(LogLevel.ERROR, mesage, ex);
    }

    @Override
    public final void logError(String format, Object... arguments) {

    }

    @Override
    public final void logSimulation(String mesage) {
        this.logSimulation(mesage, (Throwable) null);
    }

    @Override
    public final void logSimulation(String mesage, Throwable ex) {
        this.log(LogLevel.SIMULATION, mesage, ex);
    }

    @Override
    public final void logSimulation(String format, Object... arguments) {

    }

    @Override
    public final void logAll(String mesage) {
        this.logAll(mesage, (Throwable) null);
    }

    @Override
    public final void logAll(String mesage, Throwable ex) {
        if (this.level != LogLevel.DEBUG) {
            this.log(mesage, ex);
        }
    }

    @Override
    public final void logAll(String format, Object... arguments) {

    }

    protected abstract void write(String message);

    private class MessageWriter extends Writer {

        private StringBuilder str;
        MessageWriter(StringBuilder builder) {
            this.str = builder;
        }

        @Override
        public void write(char[] chars, int i, int i1) throws IOException {
            this.str.append(chars, i, i1);
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }

}
