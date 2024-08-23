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
package org.hawk.executor.command.version;

import static org.hawk.constant.HawkConstant.SVN_REVN_NO;
import static org.hawk.constant.HawkConstant.VERSION;

import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class VersionExecutor implements IHawkCommandExecutor {

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        System.out.println("hawk version " + VERSION);
        System.out.println("hawk svn revision no " + SVN_REVN_NO);
        return true;
    }
}
