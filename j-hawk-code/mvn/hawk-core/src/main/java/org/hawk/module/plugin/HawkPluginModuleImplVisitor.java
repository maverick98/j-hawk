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

package org.hawk.module.plugin;


import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;

/**
 *
 * @author Manoranjan Sahu
 */
public abstract class HawkPluginModuleImplVisitor extends ImplementorVisitor {

    public abstract void onVisit(HawkPluginModule hawkCoreModule);

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onVisit(Object instance) {
        assert instance == null;
        if (instance instanceof HawkPluginModule) {
            this.onVisit((HawkPluginModule) instance);
        } else {
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


