/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.sequence;

import java.util.HashMap;
import java.util.Map;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public  class DefaultSequenceProvider implements ISequenceProvider{
    private static final HawkLogger logger = HawkLogger.getLogger(DefaultSequenceProvider.class.getName());
    
    private final Map<Class, Integer> sequenceMap = new HashMap<>();

    @Override
    public Map<Class,Integer> getSequenceMap() {
        return sequenceMap;
    }

    @Override
    public Integer getSequence(Class scriptCacheClazz) {

        Integer sequence =  this.getSequenceMap().get(scriptCacheClazz);
        if(sequence == null){
            logger.warn("Could not find sequence for class {"+scriptCacheClazz+"}");
        }
        return sequence;
    }
}
