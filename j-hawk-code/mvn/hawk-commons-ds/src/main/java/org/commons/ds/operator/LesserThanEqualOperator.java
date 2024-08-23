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

import org.common.di.ScanMe;
import org.commons.ds.exp.IObject;


/**
 *
 * @author manosahu
 */
@ScanMe(true)
@Opr(symbol = "<=")
public class LesserThanEqualOperator extends DefaultOperator{
 public LesserThanEqualOperator(Operator supportedOperator) {
        super(supportedOperator);
    }
 public LesserThanEqualOperator() {
        super();
    }
    @Override
    public IObject operate(IObject leftObject, IObject rightObject) throws Exception {
        return leftObject.lessThanEqualTo(rightObject);
    }
}
