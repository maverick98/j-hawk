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
package org.hawk.executor.command.interpreter;




import org.hawk.executor.DefaultScriptExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ScriptExecutor extends DefaultScriptExecutor {
    
    /**
     *
     * @param hawkCommand
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        boolean status = false;
        boolean compilationStatus;
        ScriptInterpretationCommand scriptInterpretationCommand = null;
        
        if ( hawkCommand instanceof ScriptInterpretationCommand ){
            scriptInterpretationCommand = (ScriptInterpretationCommand) hawkCommand;
        }else{
            //throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String hawkScript = scriptInterpretationCommand.getHawkScriptData();

        compilationStatus = this.compile(hawkScript);

        if (compilationStatus) {
            status = this.interpret();
        } else {
            throw new Exception("compilation failed!!!");
        }

        return status;

    }
}
