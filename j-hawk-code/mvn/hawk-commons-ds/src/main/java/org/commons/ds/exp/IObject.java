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
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.exp;

/**
 *
 * @author manosahu
 */
public interface IObject {

    IObject add(IObject otherObject) throws Exception;

    IObject subtract(IObject otherObject) throws Exception;

    IObject multiply(IObject otherObject) throws Exception;

    IObject divide(IObject otherObject) throws Exception;

    IObject modulus(IObject otherObject) throws Exception;

    IObject equalTo(IObject otherObject) throws Exception;

    IObject greaterThan(IObject otherObject) throws Exception;

    IObject lessThan(IObject otherObject) throws Exception;

    IObject greaterThanEqualTo(IObject otherObject) throws Exception;

    IObject lessThanEqualTo(IObject otherObject) throws Exception;

    IObject and(IObject otherObject) throws Exception;

    IObject or(IObject otherObject) throws Exception;

    String toUI();

}
