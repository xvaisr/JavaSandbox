/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logging;

/**
 *
 * @author Roman Vais
 */
public interface MultiLogger extends Logger {

    public void appendLogger(Logger lg);
    
}
