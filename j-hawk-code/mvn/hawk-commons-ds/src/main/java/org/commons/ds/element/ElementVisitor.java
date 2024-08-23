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
package org.commons.ds.element;

import java.util.List;
import org.common.di.AppContainer;
import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;
import org.commons.implementor.InstanceVisitable;

import org.commons.reflection.ClazzUtil;


/**
 *
 * @author Manoranjan Sahu
 */
public abstract class ElementVisitor extends ImplementorVisitor {

    public abstract void onVisit(IElement element);

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onVisit(Object instance) {
        if (instance instanceof IElement) {
            this.onVisit((IElement) instance);
        } else {
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

    @Override
    public void visit(InstanceVisitable instanceVisitable) throws Exception {
        
        List<Class> all = AppContainer.getInstance().getSubTypesOf(instanceVisitable.getClazz());
        for(Class clazz : all){
            Object instance = ClazzUtil.instantiate(clazz);
            onVisit(instance);
        }
    }
}
