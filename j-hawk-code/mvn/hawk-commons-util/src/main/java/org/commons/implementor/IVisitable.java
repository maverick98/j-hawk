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

package org.commons.implementor;

/**
 *
 * @author Manoranjan Sahu
 */
public interface IVisitable {

    public void accept(IVisitor visitor) throws Exception;
}
