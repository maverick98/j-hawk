/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.function;


import org.commons.implementor.IVisitable;
import org.commons.implementor.IVisitor;

/**
 *
 * @author manosahu
 */
public abstract class AbstractFunctionNodeVisitor implements IVisitor , IFunctionNodeVisitor{

    @Override
    public abstract boolean onVisit(FunctionNode functionNode);
    
    @Override
    public void visit(IVisitable visitable) {
        if(visitable instanceof FunctionNode){
            this.onVisit((FunctionNode)visitable);
        }else{
        //    throw new HawkError("Pshyco ???");
        }
    }
    
}
