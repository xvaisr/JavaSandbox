/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logging.impl.output;

/**
 *
 * @author Roman Vais
 */
public interface LoggingOutput {

    public int write(String msg);
    public int witre(char[] ouptut);
    public int witre(byte[] data);

}
