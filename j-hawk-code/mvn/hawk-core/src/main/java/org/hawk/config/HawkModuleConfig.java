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
import org.hawk.module.cache.AllModuleCache;
import org.hawk.module.plugin.cache.IPluginModuleCache;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class HawkModuleConfig extends DefaultHawkConfig {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkModuleConfig.class.getName());
    @Autowired(required = true)
       
    HawkEventCallbackRegistry hawkPluginCallbackRegistry;
    @Autowired(required = true)
    private IAllModuleCache moduleCache;
    @Autowired(required = true)
       
    private IPluginModuleCache  pluginModuleCache;
    
    @Autowired(required = true)
       
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
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PreHawkPluginUndeploymentEvent.class, 
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            
            
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PostHawkPluginDeploymentEvent.class, 
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     PostHawkPluginUndeploymentEvent.class, 
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     HawkPluginValidationEvent.class, 
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
             this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     HawkPluginExtractionEvent.class, 
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
                                                         .getPluginDeployementCallback()
                                                 );
            this.getHawkPluginCallbackRegistry().register
                                                 (
                                                     HawkPluginLoadingEvent.class, 
                                                     AppContainer.getInstance().getBean(AllModuleCache.class).getPluginModuleCache()
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
