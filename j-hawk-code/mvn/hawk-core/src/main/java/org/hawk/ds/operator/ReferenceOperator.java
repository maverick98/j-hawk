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

package org.hawk.ds.operator;

import org.commons.ds.exp.IObject;
import org.commons.ds.operator.DefaultOperator;
import org.commons.ds.operator.Operator;
import org.commons.ds.operator.Opr;
import org.hawk.ds.exp.IHawkObject;
import org.common.di.ScanMe;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
@Opr(symbol = "->")
public class ReferenceOperator extends DefaultOperator {
    public ReferenceOperator(){}
  public ReferenceOperator(Operator supportedOperator) {
        super(supportedOperator);
    }

    @Override
    public IObject operate(IObject leftObject, IObject rightObject) throws Exception {
        IHawkObject leftHawkObject = (IHawkObject)leftObject;
        IHawkObject rightHawkObject = (IHawkObject)rightObject;
        return  leftHawkObject.refer(rightHawkObject);
    }
}
