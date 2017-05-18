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
package org.hawk.config;

import org.common.di.AppContainer;
import org.commons.event.callback.IHawkEventCallback;

import org.hawk.logger.HawkLogger;
import org.hawk.module.cache.IAllModuleCache;
import org.hawk.plugin.event.HawkPluginExtractionEvent;
import org.hawk.plugin.event.HawkPluginLoadingEvent;
import org.hawk.plugin.event.HawkPluginValidationEvent;
import org.hawk.plugin.event.PostHawkPluginDeploymentEvent;
import org.hawk.plugin.event.PostHawkPluginUndeploymentEvent;
import org.hawk.plugin.event.PreHawkPluginDeploymentEvent;
import org.hawk.plugin.event.PreHawkPluginUndeploymentEvent;
import org.common.di.ScanMe;
import org.commons.reflection.Create;
import org.commons.event.callback.HawkEventCallbackRegistry;
import org.hawk.module.plugin.cache.IPluginModuleCache;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
//@Component(HAWKMODULECONFIG)
//@Qualifier(DEFAULTQUALIFIER)
public class HawkModuleConfig extends DefaultHawkConfig {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkModuleConfig.class.getName());
    @Autowired(required = true)
    //@Qualifier(HAWKPLUGINCALLBACKREGISTRY)
    HawkEventCallbackRegistry hawkPluginCallbackRegistry;
    @Autowired(required = true)
    private IAllModuleCache moduleCache;
    @Autowired(required = true)
    //@Qualifier(MODULECACHE)
    private IPluginModuleCache  pluginModuleCache;
    
    @Autowired(required = true)
    //@Qualifier(HAWKIOEVENTCALLBACK)
    private IHawkEventCallback hawkIOEventCallback;

    public IHawkEventCallback getHawkEventCallback() {
        return hawkIOEventCallback;
    }

    public void setHawkEventCallback(IHawkEventCallback hawkEventCallback) {
        this.hawkIOEventCallback = hawkEventCallback;
    }

    public IAllModuleCache getModuleCache() {
        return moduleCache;
    }

    public void setModuleCache(IAllModuleCache moduleCache) {
        this.moduleCache = moduleCache;
    }
    

    public IPluginModuleCache getPluginModuleCache() {
        return pluginModuleCache;
    }

    public void setPluginModuleCache(IPluginModuleCache pluginModuleCache) {
        this.pluginModuleCache = pluginModuleCache;
    }

   

    public HawkEventCallbackRegistry getHawkPluginCallbackRegistry() {
        return hawkPluginCallbackRegistry;
    }

    public void setHawkPluginCallbackRegistry(HawkEventCallbackRegistry hawkPluginCallbackRegistry) {
        this.hawkPluginCallbackRegistry = hawkPluginCallbackRegistry;
    }
    
    @Override
    public boolean configure() throws Exception {
        
        try {
            boolean shouldCacheSubTasks = true;
            this.getModuleCache().cacheModules(shouldCacheSubTasks);
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PreHawkPluginDeploymentEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PreHawkPluginUndeploymentEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            
            
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PostHawkPluginDeploymentEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PostHawkPluginUndeploymentEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     HawkPluginValidationEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
             this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     HawkPluginExtractionEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     HawkPluginLoadingEvent.class, 
                                                     this.getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            
            /*  this.getHawkPluginCallbackRegistry().register
            (
            HawkOutputEvent.class,
            this.getHawkEventCallback()
            );
            this.getHawkPluginCallbackRegistry().register
            (
            HawkErrorEvent.class,
            this.getHawkEventCallback()
            );*/
                        
            
        } catch (Throwable ex) {
            throw new Exception(ex);
            //logger.error(ex);
        }
        return true;
    }

    @Override
    public Integer getSequence() {
        return super.getSequence();
    }

    @Create
    public IHawkConfig create() {
        return AppContainer.getInstance().getBean(HawkModuleConfig.class);
    }
}
