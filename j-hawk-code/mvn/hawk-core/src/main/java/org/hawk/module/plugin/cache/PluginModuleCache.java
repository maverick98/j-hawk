/**
 *  
 * left.
 *
 *    
 *  
 *
 */
package org.hawk.module.plugin.cache;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.common.di.AppContainer;
import org.commons.event.HawkEvent;
import org.commons.event.HawkEventPayload;
import org.commons.event.callback.IHawkEventCallback;
import org.commons.reflection.ClazzUtil;
import org.commons.string.StringUtil;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;
import org.hawk.module.cache.AbstractModuleCache;
import org.hawk.module.cache.AllModuleCache;
import org.hawk.module.plugin.HawkPluginModule;
import org.hawk.module.plugin.HawkPluginModuleFactory;
import org.hawk.module.plugin.IPluginModule;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.HawkPluginServiceImpl;
import org.hawk.plugin.IHawkPluginConfig;
import org.hawk.plugin.event.HawkPluginExtractionEvent;
import org.hawk.plugin.event.HawkPluginLoadingEvent;
import org.hawk.plugin.event.HawkPluginValidationEvent;
import org.hawk.plugin.event.PostHawkPluginDeploymentEvent;
import org.hawk.plugin.event.PostHawkPluginUndeploymentEvent;
import org.hawk.plugin.event.PreHawkPluginDeploymentEvent;
import org.hawk.plugin.event.PreHawkPluginUndeploymentEvent;
import org.hawk.plugin.event.callback.IHawkPluginCallbackCore;
import org.hawk.plugin.exception.HawkPluginException;
import org.hawk.plugin.metadata.HawkPluginMetaData;
import org.hawk.plugin.metadata.PluginModuleClazz;
import org.hawk.xml.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author msahu98
 */
public class PluginModuleCache extends AbstractModuleCache implements IPluginModuleCache {

    private static final HawkLogger logger = HawkLogger.getLogger(PluginModuleCache.class.getName());
    private PluginDeploymentCallback pluginDeployementCallback = new PluginDeploymentCallback();

    @Autowired(required = true)

    private HawkPluginServiceImpl hawkPluginServiceImpl;

    @Override
    public PluginDeploymentCallback getPluginDeployementCallback() {
        return pluginDeployementCallback;
    }

    public void setPluginDeployementCallback(PluginDeploymentCallback pluginDeployementCallback) {
        this.pluginDeployementCallback = pluginDeployementCallback;
    }

    public HawkPluginServiceImpl getHawkPluginService() {
        return hawkPluginServiceImpl;
    }

    public void setHawkPluginService(HawkPluginServiceImpl hawkPluginService) {
        this.hawkPluginServiceImpl = hawkPluginService;
    }

    private boolean cachePluginModulesInternal() throws Exception {
        boolean cached = false;
        Set<HawkPlugin> installedPlugins = null;
        try {
            installedPlugins = this.getHawkPluginService().findInstalledPlugins();
        } catch (Throwable ex) {
            ex.printStackTrace();
            //logger.warn("could not find installed plugins",new Object[]{ex.getMessage()});
            return false;
        }

        if (installedPlugins != null && !installedPlugins.isEmpty()) {
            this.resetModules();
            cached = this.refreshModules(true);
        }
        return cached;

    }

    @Override
    public boolean cacheModules(boolean shouldCacheSubTasks) throws Exception {
        boolean result;
        boolean moduleCached = this.cachePluginModulesInternal();
        if (moduleCached) {
            result = this.getSubTaskCache().cacheSubTasks(this.getModules());
        } else {
            result = false;
        }
        return result;
    }

    public Integer countPluginModules() {
        return this.getModules().size();
    }

    @Override
     public boolean refreshModules(boolean shouldCacheSubTasks) throws Exception{
        int before = this.countPluginModules();
        Map<Integer, IPluginModule> map = HawkPluginModuleFactory.getCachedHawkPluginModules();

        for (Map.Entry<Integer, IPluginModule> entry : map.entrySet()) {
            IPluginModule pluginModule = entry.getValue();
            String moduleName = pluginModule.getName();
            this.getModules().put(moduleName, pluginModule);
        }
       
        this.getSubTaskCache().cacheSubTasks(this.getModules());
       
        int after = this.countPluginModules();
        boolean refreshed = (after - before) > 0;
        return refreshed;
    }

    @Override
    public boolean removePlugin(HawkPlugin hawkPlugin) throws HawkPluginException {
        if (hawkPlugin == null || !hawkPlugin.validate()) {
            throw new HawkPluginException("illegal args");
        }
        boolean removed = false;
        for (Iterator<Map.Entry<String, IModule>> itr = this.getModules().entrySet().iterator(); itr.hasNext();) {
            Map.Entry<String, IModule> entry = itr.next();
            String hawkPluginName = ((IPluginModule) entry.getValue()).getPluginName();
            if (hawkPluginName.equalsIgnoreCase(hawkPlugin.getName())) {
                itr.remove();
                removed = true;
            }
        }

        return removed;

    }

    @Override
    public boolean removePlugin(String hawkPlugin) throws HawkPluginException {
        if (StringUtil.isNullOrEmpty(hawkPlugin)) {
            return false;
        }
        return this.removePlugin(new HawkPlugin(hawkPlugin));
    }

    public class PluginDeploymentCallback implements IHawkPluginCallbackCore {

        public PluginDeploymentCallback() {
        }

        @Override
        public Integer getSequence() {
            return 1;
        }

        @HawkEvent(type = PostHawkPluginDeploymentEvent.class)
        public boolean onPostHawkPluginDeployment(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            PostHawkPluginDeploymentEvent hawkPluginEvent = (PostHawkPluginDeploymentEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) hawkPluginPayload.getPayload();
            logger.info("in PostHawkPluginDeployementCallback of moduleCache");
            if (hawkPlugin != null && hawkPlugin.validate() && hawkPlugin.isExtracted()) {
                try {
                    AppContainer.getInstance().getBean(AllModuleCache.class).refreshModules(true);
                } catch (Exception ex) {
                    Logger.getLogger(PluginModuleCache.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }

        @HawkEvent(type = PostHawkPluginUndeploymentEvent.class)
        public boolean onPostHawkPluginUnDeployment(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            PostHawkPluginUndeploymentEvent hawkPluginEvent = (PostHawkPluginUndeploymentEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) hawkPluginPayload.getPayload();

            logger.info("in PostHawkPluginUnDeployementCallback of moduleCache");
            boolean status = false;
            if (hawkPlugin != null && hawkPlugin.validate()) {
                status = removePlugin(hawkPlugin);
            }
            return status;
        }

        @HawkEvent(type = PreHawkPluginDeploymentEvent.class)
        public boolean onPreHawkPluginDeployment(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            PreHawkPluginDeploymentEvent hawkPluginEvent = (PreHawkPluginDeploymentEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) hawkPluginPayload.getPayload();

            logger.info("in PreHawkPluginDeployementCallback of moduleCache");
            if (hawkPlugin != null && hawkPlugin.validate() && hawkPlugin.isExtracted()) {
                //refreshPluginModules();
            }
            return true;
        }

        @HawkEvent(type = PreHawkPluginUndeploymentEvent.class)
        public boolean onPreHawkPluginUnDeployment(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            PreHawkPluginUndeploymentEvent hawkPluginEvent = (PreHawkPluginUndeploymentEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) hawkPluginPayload.getPayload();

            logger.info("in PreHawkPluginUnDeployementCallback of moduleCache");
            boolean status = false;
            if (hawkPlugin != null && hawkPlugin.validate()) {
                //status = removePlugin(hawkPlugin);
            }
            return status;
        }

        @Override
        public int compareTo(IHawkEventCallback o) {
            return this.getSequence().compareTo(o.getSequence());
        }

        @HawkEvent(type = HawkPluginValidationEvent.class)
        public boolean onHawkPluginValidation(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            HawkPluginValidationEvent hawkPluginEvent = (HawkPluginValidationEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) hawkPluginPayload.getPayload();

            if (hawkPlugin.getValidated()) {
                System.out.println("hawk is validated .... great");
            } else {
                System.out.println("hawk is not validated .... bad");
            }
            return true;
        }

        @HawkEvent(type = HawkPluginExtractionEvent.class)
        public boolean onHawkPluginExtraction(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            HawkPluginExtractionEvent hawkPluginEvent = (HawkPluginExtractionEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) hawkPluginPayload.getPayload();

            if (hawkPlugin.isExtracted()) {
                System.out.println("hawk is extracted .... great");
            } else {
                System.out.println("hawk is not extracted .... bad");
            }
            return true;
        }

        @HawkEvent(type = HawkPluginLoadingEvent.class)
        public boolean onHawkPluginLoading(HawkEventPayload hawkPluginPayload) throws HawkPluginException {
            HawkPluginLoadingEvent hawkPluginEvent = (HawkPluginLoadingEvent) hawkPluginPayload.getEvent();
            HawkPlugin hawkPlugin = (HawkPlugin) (HawkPlugin) hawkPluginPayload.getPayload();

            if (hawkPlugin.getLoaded()) {
                try {
                    IHawkPluginConfig hawkPluginConfig = this.loadPluginConfig(hawkPlugin);

                    hawkPluginConfig.configure(hawkPlugin);

                } catch (Throwable ex) {
                    throw new HawkPluginException(ex);
                }

            } else {
                System.out.println("hawk is not loaded .... bad");
            }
            return true;
        }

        private IHawkPluginConfig loadPluginConfig(HawkPlugin hawkPlugin) throws HawkPluginException {
            HawkPluginMetaData hawkPluginMetaData = hawkPlugin.getPluginMetaData();
            IHawkPluginConfig hawkPluginConfig;
            try {
                Class configClazz = ClazzUtil.loadClass(hawkPluginMetaData.getConfiguration().getParserClazz());
                String configXML = hawkPlugin.getAbsolutePath(hawkPluginMetaData.getConfiguration().getConfigXML());
                Object instance = XMLUtil.unmarshal(configXML, configClazz);
                hawkPlugin.setConfig(instance);
                hawkPlugin.setConfigLoaded(true);
                String springConfigClazz = hawkPluginMetaData.getConfiguration().getSpringConfigClazz();
                Class springConfig = ClazzUtil.loadClass(springConfigClazz);
                AppContainer.getInstance().registerPluginConfig(hawkPlugin.getName(), springConfig);
                // AppContainer.getInstance().getAppContext().refresh();
                Class hawkPluginConfigClazz = ClazzUtil.loadClass(hawkPluginMetaData.getConfiguration().getHawkConfigClazz());

                //  hawkPluginConfig = ClazzUtil.instantiateFromSpring(hawkPluginMetaData.getConfiguration().getHawkConfigClazz(), IHawkPluginConfig.class);
                hawkPluginConfig = (IHawkPluginConfig) AppContainer.getInstance().getBean(hawkPluginConfigClazz);
                hawkPluginConfig.onLoad(hawkPlugin);

                List<PluginModuleClazz> pluginModuleClazz = hawkPluginMetaData.getPluginModuleClazz();

                pluginModuleClazz.forEach(pmc -> {
                    try {
                        HawkPluginModule moduleInstance = ClazzUtil.instantiate(pmc.getClazz(), HawkPluginModule.class);
                        HawkPluginModuleFactory.refreshHawkPluginModules(moduleInstance);
                    } catch (Exception ex) {
                        Logger.getLogger(PluginModuleCache.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

            } catch (Exception ex) {
                ex.printStackTrace();
                // logger.error(ex);
                throw new HawkPluginException(ex);
            }

            return hawkPluginConfig;
        }
    }

}
