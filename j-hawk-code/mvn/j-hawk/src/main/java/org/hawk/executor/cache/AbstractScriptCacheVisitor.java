/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.executor.cache;


import org.commons.implementor.IVisitable;

/**
 *
 * @author manosahu
 */
public  abstract class AbstractScriptCacheVisitor implements IScriptCacheVisitor{

    @Override
    public abstract  void visit(IScriptCache scriptCache) ;

    @Override
    public void visit(IVisitable visitable) {
        if(visitable instanceof IScriptCache){
            
            this.visit(((IScriptCache)visitable));
        }else{
           // throw new HawkError("You are nuts!!!");
        }
    }

  
    
}
