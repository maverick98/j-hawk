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
public class ExecFunctionElement extends ExecElement {

    public ExecFunctionElement(){
        
    }
    public ExecFunctionElement(final String element, final int pos) {
        super(element, pos);
    }

    public static ExecFunctionElement create(final String operand, final int pos) {
        ExecFunctionElement execFunctionElement = new ExecFunctionElement(operand, pos);
       // execFunctionElement.setIsVariable(true);
       // execFunctionElement.setExecFunction(true);
      //  execFunctionElement.setExecModule(false);
       // execFunctionElement.setStructAsgnmnt(false);
        return execFunctionElement;
    }
    @Override
    public String getHorizontalParserSequence() {
        return "4.1.1";
    }
     @Override
    public boolean shouldEvaluate(){
        return true;
    }
}
