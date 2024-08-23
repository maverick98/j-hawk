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
package org.commons.implementor;

import java.util.List;
import org.apache.log4j.Logger;
import org.common.di.AppContainer;
import org.commons.reflection.ClazzUtil;



/**
 *
 * @author Manoranjan Sahu
 */
public abstract class ImplementorVisitor implements IVisitor {

    private static final Logger logger = Logger.getLogger(ImplementorVisitor.class.getName());

    public abstract void onVisit(Class clazz);

    public abstract void onVisit(Object instance);

    public abstract void onVisit(String clazzStr);

    public void visit(ClazzVisitable clazzVisitable) throws Exception {
         
        List<Class> all = AppContainer.getInstance().getSubTypesOf(clazzVisitable.getClazz());
        for(Class clazz : all){
            
            onVisit(clazz);
        }


    }

    public void visit(InstanceVisitable instanceVisitable) throws Exception {
        
        List<Class> all = AppContainer.getInstance().getSubTypesOf(instanceVisitable.getClazz());
        for(Class clazz : all){
            
            Object instance = ClazzUtil.instantiateFromSpring(clazz);
            if(instance  == null){
                instance = ClazzUtil.instantiate(clazz);
            }
            onVisit(instance);
        }
    }

    public void visit(ClazzLiteralVisitable clazzLiteralVisitable) {
         
        List<Class> all = AppContainer.getInstance().getSubTypesOf(clazzLiteralVisitable.getClazz());
        for(Class clazz : all){
            
            onVisit(clazz.getName());
        }


    }
}
