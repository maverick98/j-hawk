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
package org.hawk.plugin;

import java.net.URL;
import java.util.Set;
import org.commons.event.exception.HawkEventException;
import org.hawk.executor.command.plugin.available.AvailablePluginHtmlJavaBean;
import org.hawk.plugin.exception.HawkPluginException;

/**
 *
 * @author user
 */
public interface IHawkPluginService {

    public String getPluginArchivePath(HawkPlugin hawkPlugin) throws HawkPluginException;

    public String getPluginRootDir();

    public HawkPlugin getPlugin(String pluginArchive);

    public String getPluginHome(String pluginArchive);

    /**
     * hawkPlugin contains only the archive file path.
     *
     * @param hawkPlugin
     * @return
     * @throws HawkPluginException
     * @throws org.commons.event.exception.HawkEventException
     */
    public boolean extract(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean remove(Set<HawkPlugin> hawkPlugins) throws HawkPluginException, HawkEventException;

    public boolean remove(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean load(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean loadPluginMetaData(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean deploy(String hawkPluginArchive) throws HawkPluginException, HawkEventException;

    public boolean deploy(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean deploy(Set<HawkPlugin> hawkPlugins) throws HawkPluginException, HawkEventException;

    public boolean unDeploy(String hawkPluginArchive) throws HawkPluginException, HawkEventException;

    public boolean unDeploy(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean unDeploy(Set<HawkPlugin> hawkPlugins) throws HawkPluginException, HawkEventException;

    public boolean unDeployAll() throws HawkPluginException, HawkEventException;

    public Set<AvailablePluginHtmlJavaBean> showAvailablePlugins() throws HawkPluginException, HawkEventException;

    public Set<HawkPlugin> findAvailablePlugins() throws HawkPluginException, HawkEventException;

    public boolean downloadPlugin(HawkPlugin hawkPlugin) throws HawkPluginException, HawkEventException;

    public boolean downloadPlugin(String url) throws HawkPluginException, HawkEventException;

    public Set<HawkPlugin> findDownloadedPlugins() throws HawkPluginException, HawkEventException;

    public Set<HawkPlugin> findInstalledPlugins() throws HawkPluginException, HawkEventException;

    public Set<HawkPlugin> findNYIPlugins() throws HawkPluginException, HawkEventException;

    public Set<HawkPlugin> findNYDPlugins() throws HawkPluginException, HawkEventException;

    public HawkPlugin findPlugin(String hawkPluginArchive) throws HawkPluginException, HawkEventException;

    public boolean copy(String pluginArchive) throws HawkPluginException, HawkEventException;

    public boolean download(URL pluginArchiveURL) throws HawkPluginException, HawkEventException;
}
