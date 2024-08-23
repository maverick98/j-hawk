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

import java.util.Map;
import org.commons.file.FileUtil;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
import org.hawk.logger.HawkLogger;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ScriptCompilationCommand extends HawkExecutionCommand {

    private static final HawkLogger logger = HawkLogger.getLogger(ScriptCompilationCommand.class.getName());
    private static final String REGEX = "\\s*-compile\\s*-f\\s*(.*)";
    private static final String USAGE = "-compile -f {hawk file path}";
    
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

    public ScriptCompilationCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {

        return AppContainer.getInstance().getBean(ScriptCompiler.class).execute(this);

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

    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
