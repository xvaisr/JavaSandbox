/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argparser;
import injector.injection.annotations.Singleton;
import java.util.List;

/**
 *
 * @author Roman Vais
 */
@Singleton
public interface ArgParser {
    public static enum ArgType {
        unspecifiedArg,
        argument,
        option,
        longOption
    };

    public boolean parse(String[] args);
    public void reset();

    public void setMandatoryUnspecified(String name, int index, Class<?> valueType);
    public void setMandatoryArg(String name);
    public void setMandatoryOpt(String name);
    public void setMandatoryLongOpt(String name);
    public void setOptionalArg(String name);
    public void setOptionalOpt(String name);
    public void setOptionalLongOpt(String name);

    public void setMandatoryArg(String name, Class<?> valueType);
    public void setMandatoryOpt(String name, Class<?> valueType);
    public void setMandatoryLongOpt(String name, Class<?> valueType);
    public void setOptionalArg(String name, Class<?> valueType);
    public void setOptionalOpt(String name, Class<?> valueType);
    public void setOptionalLongOpt(String name, Class<?> valueType);

    public void setMandatoryArg(String name, Class<?> valueType, boolean separated);
    public void setMandatoryOpt(String name, Class<?> valueType, boolean separated);
    public void setMandatoryLongOpt(String name, Class<?> valueType, boolean separated);
    public void setOptionalArg(String name, Class<?> valueType, boolean separated);
    public void setOptionalOpt(String name, Class<?> valueType, boolean separated);
    public void setOptionalLongOpt(String name, Class<?> valueType, boolean separated);

    public List<Argument> getArguments();
    public Argument getArgument(String name);
}
