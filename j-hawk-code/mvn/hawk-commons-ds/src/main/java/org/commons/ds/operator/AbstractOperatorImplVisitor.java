/*
 * This file is part of j-hawk
 * Copyright (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */


package org.commons.ds.operator;

import org.commons.implementor.IVisitable;
import org.commons.implementor.ImplementorVisitor;


/**
 *
 * @author manosahu
 */
public abstract class AbstractOperatorImplVisitor extends ImplementorVisitor{

    @Override
    public void onVisit(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public abstract void onVisit(IOperator instance);

    @Override
    public void onVisit(Object instance) {
        if(instance instanceof IOperator){
            this.onVisit((IOperator)instance);
        }else{
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

}
