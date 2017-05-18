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
