/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 
 */
package org.hawk.executor.command.parasite;

import org.commons.file.FileUtil;
import org.commons.reflection.ClazzUtil;
import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.IHawkExecutionCommandFactory;
import org.hawk.executor.command.interpreter.ScriptInterpretationPerfCommand;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ParasiteExecutor implements IHawkCommandExecutor {

    @Autowired(required = true)
       
    private IHawkExecutionCommandFactory hawkExecutionCommandFactory;

    public IHawkExecutionCommandFactory getHawkExecutionCommandFactory() {
        return hawkExecutionCommandFactory;
    }

    public void setHawkExecutionCommandFactory(IHawkExecutionCommandFactory hawkExecutionCommandFactory) {
        this.hawkExecutionCommandFactory = hawkExecutionCommandFactory;
    }

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {

        ParasiteCommand parasiteCommand = (ParasiteCommand) hawkCommand;
        Class containerStartupClazz = ClazzUtil.loadClass(parasiteCommand.getContainerStartupClassStr().trim());
        IContainerStartup containerStartupInstance = ClazzUtil.instantiate(containerStartupClazz, IContainerStartup.class);
        containerStartupInstance.startup();
        ScriptInterpretationPerfCommand sipCommand = new ScriptInterpretationPerfCommand();
        sipCommand.setHawkScriptFile(parasiteCommand.getHawkFile());
        sipCommand.setHawkScriptData(FileUtil.readFile(sipCommand.getHawkScriptFile()));
        return sipCommand.execute();
        

    }

}
