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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.ds.exp;

import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public interface IHawkObject extends IObject {

    IHawkObject refer(IHawkObject otherScript) throws Exception;

    IHawkObject assign(IHawkObject otherScript) throws Exception;

    IHawkObject arrayBracket(IHawkObject otherScript) throws Exception;

}
