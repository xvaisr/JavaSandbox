/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argparser.impl.parsers;

import argparser.ValueParser;
import injector.injection.annotations.Named;

/**
 *
 * @author Roman Vais
 */

@Named (name = "String")
public class StringParser  implements ValueParser{

    @Override
    public boolean isParsing(Class<?> clazz) {
        return clazz == String.class;
    }

    @Override
    public Class<?> parsing() {
        return String.class;
    }

    @Override
    public Object parseValue(String input) {
        return input;
    }


}
