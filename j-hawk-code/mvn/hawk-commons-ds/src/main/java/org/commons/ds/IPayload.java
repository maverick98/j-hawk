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
package org.commons.ds;

import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public interface IPayload {

   public boolean isCalculated();
   
    public void setValue(IObject script);

    public IObject getValue();

}
