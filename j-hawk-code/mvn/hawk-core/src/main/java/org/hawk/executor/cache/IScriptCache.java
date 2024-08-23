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
package org.hawk.executor.cache;


import org.commons.implementor.IVisitable;

/**
 *
 * @author user
 */
public interface IScriptCache extends IVisitable {

    public Integer getSequence();

    public boolean cache() throws Exception;

    public boolean reset();

    public IScriptCache create();

    
}
