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
package org.hawk.executor.cache.multiline;


import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;

/**
 *
 * @author Manoranjan Sahu
 */
public abstract class MultiLineScriptCacheVisitor extends ImplementorVisitor {

    public abstract void onVisit(IMultiLineScriptCache singleLineScript);

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onVisit(Object instance) {
        if (instance instanceof IMultiLineScriptCache) {
            this.onVisit((IMultiLineScriptCache) instance);
        } else {
            
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
