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

import java.util.Map;

/**
 *
 * @author user
 */
public interface ISequenceProvider {

    public Map<Class, Integer> getSequenceMap();

    public Integer getSequence(Class clazz);
}
