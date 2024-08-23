/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
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
