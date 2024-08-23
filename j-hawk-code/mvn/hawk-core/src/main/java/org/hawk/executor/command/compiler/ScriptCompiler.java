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
package org.hawk.executor.command.compiler;



import org.hawk.executor.DefaultScriptExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
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
