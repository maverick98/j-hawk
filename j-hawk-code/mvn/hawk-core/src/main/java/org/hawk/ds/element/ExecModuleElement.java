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
package org.hawk.ds.element;

import org.common.di.ScanMe;

/**
 *
 * @author user
 */
@ScanMe(true)
public class ExecModuleElement extends ExecElement {

    public ExecModuleElement() {
    }

    public ExecModuleElement(final String element, final int pos) {
        super(element, pos);
    }

    public static ExecModuleElement create(final String operand, final int pos) {
        ExecModuleElement execModuleElement = new ExecModuleElement(operand, pos);
       // execModuleElement.setIsVariable(true);
      //  execModuleElement.setExecFunction(false);
      //  execModuleElement.setExecModule(true);
      //  execModuleElement.setStructAsgnmnt(false);
        return execModuleElement;
    }

    @Override
    public String getHorizontalParserSequence() {
        return "4.1.2";
    }
     @Override
    public boolean shouldEvaluate(){
        return true;
    }
}
