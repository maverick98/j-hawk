

package org.hawk.module.script;

import org.hawk.exception.HawkException;
import org.hawk.logger.HawkLogger;


/**
 *
 * @version 1.0 23 Apr, 2010
 * @author msahu
 */
public class ElseIfScript extends IfScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ElseIfScript.class.getName());
    
    @Override
    public IScript execute() throws HawkException {
        return super.execute();
    }

     @Override
    public boolean isVariable() {
        return false;
    }
}




