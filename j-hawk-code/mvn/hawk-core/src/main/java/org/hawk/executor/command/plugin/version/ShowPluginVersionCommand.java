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

package org.hawk.executor.command.plugin.version;

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
   
   
public class ShowPluginVersionCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-showVersion\\s*plugin\\s*-f\\s*(.*)";
    private static final String USAGE = "-showVersion plugin -f {hawkArchive file}";
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

    public ShowPluginVersionCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(ShowPluginVersionExecutor.class).execute(this);

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


