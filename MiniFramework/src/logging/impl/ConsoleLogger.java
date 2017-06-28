/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logging.impl;

import injector.injection.annotations.Named;
import logging.Logger;

/**
 *
 * @author Roman Vais
 */

@Named(name = "default")
public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger() {
        super();
    }

    public ConsoleLogger(String name, String format, Logger.LogLevel lvl) {
        super(name, format, lvl);
    }

    @Override
    protected void write(String message) {
        Logger.LogLevel lvl = this.getLoggingLevel();
        if(lvl == LogLevel.ERROR || lvl == LogLevel.WARNING) {
            System.err.println(message);
            return;
        }
        System.out.println(message);
    }

}
