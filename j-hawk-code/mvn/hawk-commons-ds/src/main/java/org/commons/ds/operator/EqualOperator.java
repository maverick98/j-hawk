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

package org.commons.ds.operator;

import org.commons.ds.exp.IObject;
import org.common.di.ScanMe;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
@Opr(symbol = "==")
public class EqualOperator extends DefaultOperator{
 public EqualOperator(Operator supportedOperator) {
        super(supportedOperator);
    }
 public EqualOperator(){}

    @Override
    public IObject operate(IObject leftObject, IObject rightObject) throws Exception {
        return leftObject.equalTo(rightObject);
    }
}
