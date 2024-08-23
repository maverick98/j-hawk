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


package org.commons.ds.operator;

import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;


/**
 *
 * @author manosahu
 */
public abstract class AbstractOperatorImplVisitor extends ImplementorVisitor{

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public abstract void onVisit(IOperator instance);

    @Override
    public void onVisit(Object instance) {
        if(instance instanceof IOperator){
            this.onVisit((IOperator)instance);
        }else{
            throw new Error("A VERY STUPID PROGRAMMING ERROR");
        }
    }

    @Override
    public void onVisit(String clazzStr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(IVisitable visitable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
