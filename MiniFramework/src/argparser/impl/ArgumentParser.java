/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argparser.impl;

import argparser.ArgParser;
import argparser.Argument;
import argparser.ValueParser;
import injector.injection.annotations.Named;
import injector.injection.impl.Injector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import logging.Logger;
import logging.LoggerFactory;

/**
 *
 * @author Roman Vais
 */
@Named(name = "default")
public class ArgumentParser implements ArgParser{

    private static final int UNSPECIFIED = 0;
    private static final int ARGUMENTS = 1;
    private static final int OPTIONS = 2;
    private static final int LONG_OPTIONS = 3;

    private final HashMap<String, ArgumentImpl> argumentMap;
    private boolean parsed;

    public ArgumentParser() {
        this.argumentMap = new HashMap();
        this.parsed = false;
    }

    @Override
    public boolean parse(String[] args) {
        ArrayList<ArgumentImpl> argumentList =
                new ArrayList(Arrays.asList(this.argumentMap.values().toArray(new ArgumentImpl[0])));
        String[] workArray = args.clone();
        boolean success = true;

        int index = 0;
        // int processed = UNSPECIFIED;

        while (index < workArray.length && success) {
            ArgumentImpl found = null;
            for (ArgumentImpl arg : argumentList) {

                if (arg.getType() == ArgType.unspecifiedArg && arg.getIndex() == index) {
                    found = arg;
                    break;
                }

                if (arg.getFullName().matches(workArray[index])) {
                    found = arg;
                    break;
                }

                if (workArray[index].startsWith(arg.getFullName()) && !arg.isSeparated()) {
                    found = arg;
                    break;
                }
            }

            if (found == null) {
                success = false;
                continue;
            }

            argumentList.remove(found);

            if(!found.hasValue()) {
                found.setParsedValue(true);
                index++;
                continue;
            }

            if (found.isSeparated()) {
                index++;
            }
            else {
                workArray[index] = workArray[index].substring(workArray[index].indexOf("=") + 1);
            }

            ValueParser p = Injector.inject(ValueParser.class, found.getValueType().getSimpleName());

            try {
                found.setParsedValue(p.parseValue(workArray[index]));
            }
            catch(Exception ex) {
                success = false;
                LoggerFactory lf = Injector.inject(LoggerFactory.class);
                Logger logger = lf.getLogger(this.getClass());
                logger.logDebug("Error during parsing of arguments.", ex);
            }

            index++;
        }

        for (ArgumentImpl arg : argumentList) {
            if (!arg.isOptional()) {
                success = false;
                break;
            }
        }

        return success;
    }


    @Override
    public void reset() {
        this.argumentMap.forEach((name, arg) -> {arg.setParsedValue(null); } );
        this.parsed = false;
    }

    @Override
    public void setMandatoryUnspecified(String name, int index, Class<?> valueType) {
        ArgumentImpl arg = new ArgumentImpl(index, name, ArgType.unspecifiedArg, valueType, false, false, null);
        this.argumentMap.put(name, arg);
    }

    @Override
    public void setMandatoryArg(String name) {
        this.setMandatoryArg(name, null, false);
    }

    @Override
    public void setMandatoryArg(String name, Class<?> valueType) {
        this.setMandatoryArg(name, valueType, true);
    }

    @Override
    public void setMandatoryArg(String name, Class<?> valueType, boolean separated) {
        this.setArgumentRepresentation(name, ArgType.argument, valueType, false, separated);
    }

    @Override
    public void setMandatoryOpt(String name) {
        this.setMandatoryOpt(name, null, false);
    }

    @Override
    public void setMandatoryOpt(String name, Class<?> valueType) {
        this.setMandatoryOpt(name, valueType, true);
    }

    @Override
    public void setMandatoryOpt(String name, Class<?> valueType, boolean separated) {
        this.setArgumentRepresentation(name, ArgType.option, valueType, false, separated);
    }

    @Override
    public void setMandatoryLongOpt(String name) {
        this.setMandatoryLongOpt(name, null, false);
    }

    @Override
    public void setMandatoryLongOpt(String name, Class<?> valueType) {
        this.setMandatoryLongOpt(name, valueType, true);
    }

    @Override
    public void setMandatoryLongOpt(String name, Class<?> valueType, boolean separated) {
        this.setArgumentRepresentation(name, ArgType.longOption, valueType, false, separated);
    }

    @Override
    public void setOptionalArg(String name) {
        this.setOptionalArg(name, null, false);
    }

    @Override
    public void setOptionalArg(String name, Class<?> valueType) {
        this.setOptionalArg(name, valueType, true);
    }

    @Override
    public void setOptionalArg(String name, Class<?> valueType, boolean separated) {
        this.setArgumentRepresentation(name, ArgType.argument, valueType, true, separated);
    }

    @Override
    public void setOptionalOpt(String name) {
        this.setOptionalOpt(name, null, false);
    }

    @Override
    public void setOptionalOpt(String name, Class<?> valueType) {
        this.setOptionalOpt(name, valueType, true);
    }

    @Override
    public void setOptionalOpt(String name, Class<?> valueType, boolean separated) {
        this.setArgumentRepresentation(name, ArgType.option, valueType, true, separated);
    }

    @Override
    public void setOptionalLongOpt(String name) {
        this.setOptionalLongOpt(name, null, false);
    }

    @Override
    public void setOptionalLongOpt(String name,  Class<?> valueType) {
        this.setOptionalLongOpt(name, valueType, true);
    }

    @Override
    public void setOptionalLongOpt(String name, Class<?> valueType, boolean separated) {
        this.setArgumentRepresentation(name, ArgType.longOption, valueType, true, separated);
    }

    private void setArgumentRepresentation(String name, ArgParser.ArgType type, Class<?> valType,
                                      boolean optional, boolean separated)
    {
        ArgumentImpl arg = new ArgumentImpl(name, type, valType, optional, separated);
        this.argumentMap.put(name, arg);
    }

    @Override
    public List<Argument> getArguments() {
        return new ArrayList(Arrays.asList(this.argumentMap.values().toArray(new ArgumentImpl[0])));
    }

    @Override
    public Argument getArgument(String name) {
        return this.argumentMap.get(name);
    }

}
