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

package org.hawk.executor.command.plugin.author;

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
   
   
public class ShowPluginAuthorCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-showAuthor\\s*plugin\\s*-f\\s*(.*)";
    private static final String USAGE = "-showAuthor plugin -f {hawkArchive file}";
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

    public ShowPluginAuthorCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(ShowPluginAuthorExecutor.class).execute(this);

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


