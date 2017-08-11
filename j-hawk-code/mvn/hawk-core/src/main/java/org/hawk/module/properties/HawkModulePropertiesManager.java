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
package org.hawk.module.properties;

import java.util.Properties;
import org.hawk.cache.HawkCache;
import static org.hawk.constant.HawkConstant.HAWK;
import static org.hawk.constant.HawkConstant.HAWKCONF;
import static org.hawk.constant.HawkConstant.MAIN_MODULE;
import static org.hawk.constant.HawkConstant.SEPARATOR;
import static org.hawk.constant.HawkConstant.TARGET_APP_KEY;
import org.hawk.logger.HawkLogger;
import org.hawk.main.HawkTargetSetting;
import org.common.properties.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This caches all the configs of hawk and target application as well.
 *
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 * @see HawkConfig
 */
   
   
public class HawkModulePropertiesManager {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkModulePropertiesManager.class.getName());
    /**
     * cache to store configs.
     */
    private HawkCache configs = new HawkCache();
    /**
     * target application name
     */
    private String targetApp;
    /**
     * Reference to target module's pacakge
     */
    private String targetModulePackage;
    /**
     * Reference to target main module
     */
    private String targetMainModule;
    @Autowired(required = true)
       
    private HawkTargetSetting hawkTargetSetting;

    public HawkTargetSetting getHawkTargetSetting() {
        return hawkTargetSetting;
    }

    public void setHawkTargetSetting(HawkTargetSetting hawkTargetSetting) {
        this.hawkTargetSetting = hawkTargetSetting;
    }

    /**
     * This returns the target application's name
     *
     * @param props Properties containing all key value pair
     * @return target app's name
     */
    private String getTargetApp(Properties props) {
        if (props == null) {
            return null;
        }
        targetApp = props.getProperty(TARGET_APP_KEY);
        return targetApp;
    }

    /**
     * This returns the target app's name
     *
     * @return returns the target app's name
     */
    public String getTargetApp() {
        if (targetApp == null) {
            targetApp = this.getTargetApp(this.getHawkProperties());
        }

        return targetApp;
    }

    public Properties getHawkProperties() {
        Properties properties;
        String props = this.getHawkTargetSetting().getProperty(HAWKCONF);
        if (props != null) {
            properties = PropertiesUtil.loadProperties(this.getHawkTargetSetting().getProperty(HAWKCONF));
        } else {
            properties = null;
        }
        return properties;

    }

    /**
     * This caches the configs
     */
    /*
    public boolean cacheConfig() throws Exception {
        if (configs != null && !configs.isEmpty()) {
            return false;
        }
        Properties props = this.getHawkProperties();
        if (props == null) {
            logger.warn("Could  not find hawk config file... continueing anyway....");
            AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).cacheHawkCoreModules();
            AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).cacheSubTasks();
            return false;
        }

        Map<String, IModule> modules = AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).getModules(props);


        this.getTargetApp(props);

        for (Entry<String, IModule> entry : modules.entrySet()) {

            StringBuilder sb = new StringBuilder(HAWK);
            sb.append(SEPARATOR);
            sb.append(targetApp);
            sb.append(SEPARATOR);
            sb.append(entry.getKey());
            sb.append(SEPARATOR);
            String configPrefix = sb.toString();
            String moduleConfigPackage = props.getProperty(TARGET_MODULE_CONFIG_PACKAGE_KEY);
            cache(entry.getKey(), props, moduleConfigPackage, configPrefix);

        }

        this.overrideModulesExecutionSequence();
        this.cacheHawkConfig(props);
        this.cacheTargetAppConfig(props);
        return true;
    }
      */ 

    /**
     * This caches the config of a module. The Config class for a module needs
     * to following syntax Module name = SampleModule It's config class will be
     * SampleModuleConfig
     *
     * @param module for which configs are being cached.
     * @param props contains the whole perf.conf key value pair
     * @param moduleConfigPackage module's config package
     * @param configPrefix configPrefix
     */
    /*
    private void cache(String module, Properties props,
            String moduleConfigPackage, String configPrefix) throws Exception {
        String configClazzStr = moduleConfigPackage + SEPARATOR + module + "Config";
        Class configClazz = null;
        List<Field> allFieldsList = new ArrayList<Field>();
        Field allFields[];
        configClazz = ClazzUtil.loadClass(configClazzStr);
        if (configClazz == null) {
            configClazz = HawkModuleProperties.class;
        }
        allFieldsList.addAll(Arrays.asList(configClazz.getDeclaredFields()));


        if (configClazz != null) {
            Object instance = null;
            try {
                instance = configClazz.newInstance();
            } catch (Exception ex) {
                logger.error("Could not create config object... Hawk is not guranteed to work properly", ex);
            }
            if (instance != null) {
                allFieldsList.addAll(Arrays.asList(configClazz.getFields()));
                allFields = allFieldsList.toArray(new Field[allFieldsList.size()]);

                HawkModuleProperties instanceConfig = (HawkModuleProperties) instance;
                instanceConfig.setModuleName(module);
                for (Field field : allFields) {

                    if (field.isAnnotationPresent(Config.class)) {
                        String configSufix = field.getAnnotation(Config.class).value();
                        String key = configPrefix + configSufix;
                        String value = (String) props.get(key);
                        if (value != null) {
                            try {

                                String type = field.getType().getName();

                                if (int.class.getName().equals(type)) {
                                    int valueInt = Integer.parseInt(value);
                                    field.setInt(instance, valueInt);
                                } else if (long.class.getName().equals(type)) {
                                    long valueLong = Long.parseLong(value);
                                    field.setLong(instance, valueLong);
                                } else if (String.class.getName().equals(type)) {
                                    field.set(instance, value);
                                }
                            } catch (IllegalAccessException ex) {
                                logger.error("Exception occurred", ex);
                            }
                        }
                        configs.put(module, instance);
                    }
                }
            }
        }
    }
    */
    /**
     * This cachces hawk configs from properties
     *
     * @param props
     */
    /*
    private void cacheHawkConfig(Properties props) throws Exception {


        String hawkModuleName = getHawkMainModule();

        String configPackage = HAWK_CONFIG_PACKAGE;



        this.cache(
                hawkModuleName,
                props,
                configPackage,
                hawkModuleName);
    }
    */ 

    /**
     * This returns target application's main module
     *
     * @param props containing hawk.conf key value pair
     * @return returns target application's main module
     */
    /*
    private String getTargetMainModule(Properties props) {

        targetMainModule = props.getProperty(HAWK_TARGET_MAIN_MODULE);
        return targetMainModule;
    }
    */ 

    /**
     * This returns target application's main module
     *
     * @return returns target application's main module
     */
    public String getTargetMainModule() {
        return targetMainModule;
    }

    /**
     * This returns target application's module package
     *
     * @return returns target application's module package
     */
    public String getTargetModulePackage() {


        return targetModulePackage;
    }

    /**
     * Returns Hawk main module
     *
     * @return returns hawk main module
     */
    public String getHawkMainModule() {
        String moduleName = HAWK + SEPARATOR + MAIN_MODULE;
        return moduleName;
    }

    /**
     * This caches target applications's configs
     *
     * @param props containing hawk.conf key value pair
     */
    /*
    private void cacheTargetAppConfig(Properties props) throws Exception {

        String targetModuleName = getTargetMainModule(props);
        String moduleConfigPackage = props.getProperty(TARGET_MODULE_CONFIG_PACKAGE_KEY);


        this.cache(
                targetModuleName,
                props,
                moduleConfigPackage,
                HAWK + SEPARATOR + targetModuleName + SEPARATOR);

    }
    */ 

    /**
     * This overrides the module execution sequence as defined in the perf.conf
     * which is provided as -D option with java
     */
    /*
    private void overrideModulesExecutionSequence() throws Exception {

        Set<HawkModuleProperties> sortedHawkConfigs = new TreeSet<HawkModuleProperties>();


        for (Iterator itr = configs.keySet().iterator(); itr.hasNext();) {
            String module = (String) itr.next();
            if (!HawkCoreModuleFactory.isHawkCoreModule(module)) {
                sortedHawkConfigs.add(configs.get(module, HawkModuleProperties.class));
            }
        }




        Map<String, IModule> cachedModules = AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).getCachedTargetAppModules();
        Map<String, IModule> updatedModules = cachedModules;
        if (sortedHawkConfigs.isEmpty()) {

            updatedModules = AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).getModules();
        } else {
            for (HawkModuleProperties xpr : sortedHawkConfigs) {
                updatedModules.put(xpr.getModuleName(), cachedModules.get(xpr.getModuleName()));
            }
        }

        AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).setCachedTargetAppModules(updatedModules);
        AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class).cacheHawkCoreModules(new TreeMap<Integer, IModule>());
    }
    */ 

    /**
     * This returns map containing all configs.
     *
     * @return returns map containing all configs.
     */
    /*
    public HawkCache getModuleConfig() throws Exception {
        if (configs == null || configs.isEmpty()) {
            cacheConfig();
        }
        return configs;
    }
    */ 
}
