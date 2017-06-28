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
@Named (name = "Integer")
public class IntParser  implements ValueParser{

    @Override
    public boolean isParsing(Class<?> clazz) {
        return clazz == Integer.class;
    }

    @Override
    public Class<?> parsing() {
        return Integer.class;
    }

    @Override
    public Object parseValue(String input) {
        return Integer.valueOf(input);
    }

}
