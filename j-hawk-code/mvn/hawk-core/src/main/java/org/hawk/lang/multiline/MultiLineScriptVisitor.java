/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.lang.multiline;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.common.di.AppContainer;


import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;
import org.commons.implementor.InstanceVisitable;
import org.commons.reflection.ClazzUtil;


/**
 *
 * @author Manoranjan Sahu
 */
public abstract class MultiLineScriptVisitor extends ImplementorVisitor {

    public abstract void onVisit(MultiLineScript singleLineScript);

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onVisit(Object instance) {
        if (instance instanceof MultiLineScript) {
            this.onVisit((MultiLineScript) instance);
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

    @Override
    public void visit(InstanceVisitable instanceVisitable) throws Exception {
        /*
        if (instanceVisitable.getClazz().isAnnotationPresent(Implementors.class)) {
            Implementors implementors = (Implementors) instanceVisitable.getClazz().getAnnotation(Implementors.class);

            for (Implementor implementor : implementors.value()) {

                String clazzStr = implementor.clazz();
                Object instance = ClazzUtil.instantiate(clazzStr);
                onVisit(instance);
            }
        }
        */
        List<Class> all = AppContainer.getInstance().getSubTypesOf(instanceVisitable.getClazz());
        for(Class clazz : all){
            Object instance = null;
            try {
                instance = ClazzUtil.instantiate(clazz);
            } catch (Exception ex) {
                Logger.getLogger(MultiLineScriptVisitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            onVisit(instance);
        }
    }
}
