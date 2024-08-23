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

import org.commons.ds.element.IElement;
import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;


/**
 *
 * @author manosahu
 */
public abstract class PostfixExpressionVisitor extends ImplementorVisitor{

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public abstract void onVisit(IElement instance);
    @Override
    public void onVisit(Object instance) {
        if(instance instanceof IElement){
            this.onVisit((IElement)instance);
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
