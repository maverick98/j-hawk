/*
 * This file is part of j-hawk
 *  

 * 
 *
 * 
 */
package org.hawk.module.container;

import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;

/**
 *
 * @author Manoranjan Sahu
 */
public abstract class HawkContainerModuleImplVisitor extends ImplementorVisitor {

    public abstract void onVisit(HawkContainerModule hawkContainerModule);

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onVisit(Object instance) {
        if (instance instanceof HawkContainerModule) {
            this.onVisit((HawkContainerModule) instance);
        } else {
            System.out.println("MS " + instance);
            System.out.println(instance.getClass());
            //  
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
