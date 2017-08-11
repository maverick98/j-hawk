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
package org.hawk.plugin;

import java.net.URL;
import java.util.Set;
import org.commons.event.exception.HawkEventException;
import org.hawk.plugin.exception.HawkPluginException;

/**
 *
 * @author user
 */
public interface IHawkPluginService {

    public String getPluginArchivePath(HawkPlugin hawkPlugin) throws HawkPluginException;
    public String getPluginRootDir();

    public String getPluginHome(String pluginArchive);

    /**
     * hawkPlugin contains only the archive file path.
     *
     * @param hawkPlugin
     * @return
     * @throws HawkPluginException
     */
    public boolean extract(HawkPlugin hawkPlugin) throws HawkPluginException,HawkEventException;

    public boolean remove(HawkPlugin hawkPlugin) throws HawkPluginException,HawkEventException;

    public boolean load(HawkPlugin hawkPlugin) throws HawkPluginException,HawkEventException;

    public boolean deploy(String hawkPluginArchive) throws HawkPluginException,HawkEventException;

    public boolean deploy(HawkPlugin hawkPlugin) throws HawkPluginException,HawkEventException;

    public boolean deploy(Set<HawkPlugin> hawkPlugins) throws HawkPluginException,HawkEventException;

    public boolean unDeploy(String hawkPluginArchive) throws HawkPluginException,HawkEventException;

    public boolean unDeploy(HawkPlugin hawkPlugin) throws HawkPluginException,HawkEventException;

    public boolean unDeploy(Set<HawkPlugin> hawkPlugins) throws HawkPluginException,HawkEventException;

    public boolean unDeployAll() throws HawkPluginException,HawkEventException;

    public Set<HawkPlugin> findAvailablePlugins() throws HawkPluginException,HawkEventException;
    
    public boolean downloadPlugin(HawkPlugin hawkPlugin) throws HawkPluginException,HawkEventException;

    public Set<HawkPlugin> findDownloadedPlugins() throws HawkPluginException,HawkEventException;

    public Set<HawkPlugin> findInstalledPlugins() throws HawkPluginException,HawkEventException;

    public Set<HawkPlugin> findNYIPlugins() throws HawkPluginException,HawkEventException;

    public Set<HawkPlugin> findNYDPlugins() throws HawkPluginException ,HawkEventException;
    
    public HawkPlugin findPlugin(String hawkPluginArchive) throws HawkPluginException,HawkEventException;

    public boolean copy(String pluginArchive) throws HawkPluginException,HawkEventException;

    public boolean download(URL pluginArchiveURL) throws HawkPluginException,HawkEventException;
}
