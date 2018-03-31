/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.executor.command.interpreter;

import java.util.Map;

import org.commons.file.FileUtil;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;

import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
public class ScriptInterpretationCommand extends HawkExecutionCommand {

    private static final HawkLogger logger = HawkLogger.getLogger(ScriptInterpretationCommand.class.getName());

    private String hawkScriptFile;

    private String hawkScriptData;

    public String getHawkScriptFile() {
        return hawkScriptFile;
    }

    public void setHawkScriptFile(String hawkScriptFile) {
        this.hawkScriptFile = hawkScriptFile;
    }

    public String getHawkScriptData() {
        return hawkScriptData;
    }

    public void setHawkScriptData(String hawkScriptData) {
        this.hawkScriptData = hawkScriptData;
    }

    public ScriptInterpretationCommand(String regEx) {
        super(regEx);
    }

    public ScriptInterpretationCommand() {
        super();
    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(ScriptExecutor.class).execute(this);
    }

    @Override
    public boolean onCommandFound(Map<Integer, String> map) {
        String scriptFile = map.get(1);
        this.setHawkScriptFile(scriptFile);

        this.setHawkScriptData(FileUtil.readFile(scriptFile));

        return true;
    }

    @Override
    public boolean shouldCacheConfig() {
        return true;
    }
}
