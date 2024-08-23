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



/**
 *
 * @author Manoranjan Sahu
 */
public class InstanceVisitable implements IVisitable{

    @Override
    public void accept(IVisitor implementorVisitor) throws Exception{
        implementorVisitor.visit(this);
    }

    private Class clazz;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

}
