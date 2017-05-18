/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.plugin;

import org.commons.event.callback.IHawkEventCallbackRegistry;
import org.hawk.plugin.exception.HawkPluginException;

/**
 *
 * @author manosahu
 */
public interface IHawkPluginConfig {

    public boolean onLoad(HawkPlugin hawkPlugin) throws HawkPluginException;

    public boolean configure(HawkPlugin hawkPlugin) throws HawkPluginException;

    public boolean register(IHawkEventCallbackRegistry hawkPluginCallbackRegistry) throws HawkPluginException;

}
