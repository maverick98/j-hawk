/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.module.plugin.cache;

import org.commons.event.callback.IHawkEventCallback;
import org.hawk.module.cache.IModuleCache;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.exception.HawkPluginException;

/**
 *
 * @author msahu98
 */
public interface IPluginModuleCache extends IModuleCache {

    public IHawkEventCallback getPluginDeployementCallback();

   

    public boolean removePlugin(HawkPlugin hawkPlugin) throws HawkPluginException;

    public boolean removePlugin(String hawkPlugin) throws HawkPluginException;
}
