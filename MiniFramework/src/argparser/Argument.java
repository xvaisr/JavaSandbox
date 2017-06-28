/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argparser;

/**
 *
 * @author Roman Vais
 */
public interface Argument {

    public String getName();
    public String getFullName();
    public ArgParser.ArgType getType();
    public Class<?> getValueType();
    public boolean hasValue();
    public boolean isParsed();
    public Object getValue();
    public <T> T getValue(Class<T> clazz);

}
