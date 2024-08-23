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
package org.hawk.executor.command.plugin.undeploy;

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
   
   
public class PluginUnDeploymentCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-undeploy\\s*plugin\\s*-f\\s*(.*)";
    private static final String USAGE = "-undeploy plugin -f {hawk plugin archive}";
    private String pluginArchive;

    public String getPluginArchive() {
        return pluginArchive;
    }

    public void setPluginArchive(String pluginArchive) {
        this.pluginArchive = pluginArchive;
    }

    public PluginUnDeploymentCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
          return AppContainer.getInstance().getBean(PluginUnDeploymentExecutor.class).execute(this);

    }

    @Override
    public boolean onCommandFound(Map<Integer, String> map) {
        String pluginArchivePath = map.get(1);
        this.setPluginArchive(pluginArchivePath);
        return true;
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
