/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package argparser;

import injector.injection.annotations.Singleton;

/**
 *
 * @author Roman Vais
 */
@Singleton
public interface ValueParser {

    public boolean isParsing(Class <?> clazz);
    public Class<?> parsing();
    public Object parseValue(String input);

}
