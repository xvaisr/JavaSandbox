/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logging.impl;

import java.util.concurrent.ConcurrentHashMap;
import injector.injection.annotations.Named;
import injector.injection.impl.Injector;
import logging.LoggerFactory;
import logging.Logger;

/**
 *
 * @author Roman Vais
 */
@Named(name = "default")
public class DefaultLoggerFactory implements LoggerFactory {

    public static final String DEFAULT_FORMAT = "#{name} [#{level}]: #{message} #{stack}";
    public static final Logger.LogLevel DEFAULT_LEVEL = Logger.LogLevel.DEBUG;

    private final ConcurrentHashMap<String, Logger> dployedLoggers;
    private String formatStr;
    private Logger.LogLevel level;
    private boolean changeOnCall;

    public DefaultLoggerFactory() {
        this.dployedLoggers = new ConcurrentHashMap<>();
        this.formatStr = DEFAULT_FORMAT;
        this.level = DEFAULT_LEVEL;
        this.changeOnCall = false;
    }

    @Override
    public boolean setChangeOnCall(boolean change) {
        boolean old = this.changeOnCall;
        this.changeOnCall = change;
        return old;
    }

    @Override
    public final boolean setDefaultLoggingLevel(Logger.LogLevel level) {
        if (level == null) return false;
        this.level = level;
        return true;
    }

    @Override
    public final boolean setDefaultFormatString(String format) {
        if (format == null || format.isEmpty()) return false;
        this.formatStr = format;
        return true;
    }

    @Override
    public Logger getLogger(String name) {
        return this.getLogger(name, this.level, this.formatStr);
    }

    @Override
    public Logger getLogger(String name, Logger.LogLevel level) {
        return this.getLogger(name, level, this.formatStr);
    }

    @Override
    public Logger getLogger(String name, Logger.LogLevel level, String format) {
        Logger logger;
        if (this.dployedLoggers.containsKey(name)) {
            logger = this.dployedLoggers.get(name);
            if (this.changeOnCall) {
                logger.setFormat(format);
                logger.setLoggingLevel(level);
            }
        }
        else {
            logger = Injector.inject(Logger.class, Injector.DEFAULT_NAME, name, format, level);
            this.dployedLoggers.put(name, logger);
        }
        return logger;
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return this.getLogger(clazz.getName());
    }

    @Override
    public Logger getLogger(Class<?> clazz, Logger.LogLevel level) {
        return this.getLogger(clazz.getName(), level, this.formatStr);
    }

    @Override
    public Logger getLogger(Class<?> clazz, Logger.LogLevel level, String format) {
        return this.getLogger(clazz.getName(), level, format);
    }

}
