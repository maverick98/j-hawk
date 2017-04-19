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
package org.hawk.executor.command.version;

import static org.hawk.constant.HawkConstant.SVN_REVN_NO;
import static org.hawk.constant.HawkConstant.VERSION;

import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
//@Component(VERSIONEXECUTOR)
//@Qualifier(DEFAULTQUALIFIER)
public class VersionExecutor implements IHawkCommandExecutor {

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        System.out.println("hawk version " + VERSION);
        System.out.println("hawk svn revision no " + SVN_REVN_NO);
        return true;
    }
}
