/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logging;

import injector.injection.annotations.Singleton;

/**
 *
 * @author Roman Vais
 */
@Singleton
public interface LoggerFactory {

    public boolean setChangeOnCall(boolean change);
    public boolean setDefaultLoggingLevel(Logger.LogLevel level);
    public boolean setDefaultFormatString(String format);

    public  Logger getLogger(String name);
    public  Logger getLogger(String name, Logger.LogLevel level);
    public  Logger getLogger(String name, Logger.LogLevel level, String format);
    public  Logger getLogger(Class<?> clazz);
    public  Logger getLogger(Class<?> clazz, Logger.LogLevel level);
    public  Logger getLogger(Class<?> clazz, Logger.LogLevel level, String format);

}
