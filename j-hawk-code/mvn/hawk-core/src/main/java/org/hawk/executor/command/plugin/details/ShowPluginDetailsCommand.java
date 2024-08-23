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

package org.hawk.executor.command.plugin.details;

import java.util.Map;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ShowPluginDetailsCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-showDetails\\s*plugin\\s*-f\\s*(.*)";
    private static final String USAGE = "-showDetails plugin -f {hawkArchive file}";
    private String pluginArchive;

    public String getPluginArchive() {
        return pluginArchive;
    }

    public void setPluginArchive(String pluginArchive) {
        this.pluginArchive = pluginArchive;
    }
    @Override
    public boolean onCommandFound(Map<Integer, String> map) {
        String pluginArchivePath = map.get(1);
        this.setPluginArchive(pluginArchivePath);
        return true;
    }

    public ShowPluginDetailsCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(ShowPluginDetailsExecutor.class).execute(this);

    }

    
    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
    @Override
    public boolean shouldCacheConfig() {
        return true;
    }
}


