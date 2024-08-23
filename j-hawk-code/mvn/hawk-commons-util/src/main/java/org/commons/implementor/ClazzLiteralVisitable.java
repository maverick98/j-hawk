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
public class ClazzLiteralVisitable implements IVisitable {

    private Class clazz;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void accept(IVisitor implementorVisitor)  {
        implementorVisitor.visit(this);
    }
}
