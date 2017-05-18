/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */

package org.hawk.config;


import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;

/**
 *
 * @author Manoranjan Sahu
 */
public abstract class HawkConfigVisitor extends ImplementorVisitor {

    public abstract void onVisit(IHawkConfig hawkConfig);

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onVisit(Object instance) {
        if (instance instanceof IHawkConfig) {
            this.onVisit((IHawkConfig) instance);
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
