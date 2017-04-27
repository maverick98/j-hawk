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
package org.hawk.executor.command.compiler;



import org.hawk.executor.DefaultScriptExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
//@Component(SCRIPTCOMPILER)
//@Qualifier(DEFAULTQUALIFIER)
public class ScriptCompiler extends DefaultScriptExecutor {
   
    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        boolean status;
        boolean compilationStatus;
        ScriptCompilationCommand scriptCompilationCommand = null;
        if ( hawkCommand instanceof ScriptCompilationCommand ){
            scriptCompilationCommand = (ScriptCompilationCommand) hawkCommand;
        }else{
          //  throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String hawkScript = scriptCompilationCommand.getHawkScriptData();

        compilationStatus = this.compile(hawkScript);

        if (compilationStatus) {
            status = true;
            System.out.println("Compiled successfully!!!");
        } else {
            status = false;
            System.out.println("Compiled with failure!!!");
        }

        return status;

    }
}
