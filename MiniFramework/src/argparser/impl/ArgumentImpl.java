/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argparser.impl;

import argparser.ArgParser;
import argparser.Argument;
import injector.injection.annotations.Named;


/**
 *
 * @author Roman Vais
 */
@Named(name = "default")
public class ArgumentImpl implements Argument {
    public static final int NO_INDEX = -1;

    private final ArgParser.ArgType type;
    private final String name;
    private final Class<?> valType;
    private final boolean optional;
    private final boolean separated;
    private Object value;
    private final int index;

    public ArgumentImpl(String name, ArgParser.ArgType type, Class<?> valType, boolean optional, boolean separated)
    {
        this(NO_INDEX, name, type, valType, optional, separated, null);
    }

    public ArgumentImpl(String name, ArgParser.ArgType type, Class<?> valType, boolean optional,
                    boolean separated, Object value)
    {
        this(NO_INDEX, name, type, valType, optional, separated, value);
    }

    public ArgumentImpl(int index, String name, ArgParser.ArgType type, Class<?> valType,
                    boolean optional, boolean separated, Object value)
    {
        this.index = index;
        this.name = name;
        this.type = type;
        this.valType = valType;
        this.optional = optional;
        this.separated = separated;
        this.value = null;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getFullName() {
        return this.getPrefix() + this.name;
    }

    @Override
    public ArgParser.ArgType getType() {
        return this.type;
    }

    @Override
    public Class<?> getValueType() {
        return this.valType;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public boolean isSeparated() {
        return this.optional;
    }

    @Override
    public boolean hasValue() {
        return this.valType != null;
    }

    @Override
    public boolean isParsed() {
        return this.value != null;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    public boolean setParsedValue(Object val) {
        if(this.valType.isInstance(val)) {
            this.value = val;
            return true;
        }
        return false;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if(clazz.isInstance(this.value)) {
            return clazz.cast(this.value);
        }
        return null;
    }

    public String getPrefix() {
        if (this.type == ArgParser.ArgType.option) return "-";
        if (this.type == ArgParser.ArgType.longOption) return "--";
        return "";
    }
}
