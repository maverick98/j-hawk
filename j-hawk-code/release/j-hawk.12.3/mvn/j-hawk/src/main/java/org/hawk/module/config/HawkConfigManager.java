


package org.hawk.module.config;

import org.hawk.logger.HawkLogger;
import java.util.TreeMap;
import java.util.Arrays;
import org.hawk.util.PropertiesUtil;
import org.hawk.cache.HawkCache;
import org.hawk.module.IModule;
import org.hawk.module.ModuleFactory;
import org.hawk.module.annotation.Config;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import java.util.logging.Level;
import org.hawk.module.LibraryModule;
import static org.hawk.constant.HawkConstant.*;

/**
 * This caches all the configs of hawk and target application as well.
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 * @see HawkConfig
 */
public class HawkConfigManager {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkConfigManager.class.getName());

    private static final HawkConfigManager theInstance = new HawkConfigManager();

    private HawkConfigManager(){

    }
    public static HawkConfigManager getInstance(){
        return theInstance;
    }
    /**
     * cache to store configs.
     */
    private  HawkCache configs = new HawkCache();


    /**
     * target application name
     */
    private  String targetApp ;

    /**
     * This returns the target application's name
     * @param props Properties containing all key value pair 
     * @return target app's name
     */
    public  String getTargetApp(Properties props){
        if(props == null){
            return null;
        }
        targetApp = props.getProperty(TARGET_APP_KEY);
        return targetApp;
    }
    /**
     * This returns the target app's name
     * @return returns the target app's name
     */
    public  String getTargetApp(){

        
        return targetApp;
    }

    public Properties getHawkProperties(){
        String hawkConfPath = System.getProperty(HAWKCONF);
        if(hawkConfPath == null || hawkConfPath.isEmpty()){
            return null;
        }
        return PropertiesUtil.loadProperties(hawkConfPath);

    }
    /**
     * This caches the configs 
     */
    public  boolean cacheConfig(){
        if(configs != null && !configs.isEmpty()){
            return false;
        }
        Properties props = this.getHawkProperties();
        if(props == null){
            logger.warn("Could  not find hawk config file... continueing anyway....");
            ModuleFactory.getInstance().cacheLibraryModules();
            return false;
        }
        
        Map<String,IModule> modules = ModuleFactory.getInstance().getModules(props);

        
        this.getTargetApp(props);

        for(Entry<String,IModule>entry:modules.entrySet()){

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

    /**
     * This caches the config of a module.
     * The Config class for a module needs to following syntax
     * Module name = SampleModule
     * It's config class will be SampleModuleConfig
     * @param module for which configs are being cached.
     * @param props contains the whole perf.conf key value pair
     * @param moduleConfigPackage  module's config package
     * @param configPrefix configPrefix
     */
    private  void cache(String module,Properties props,
            String moduleConfigPackage , String configPrefix){
            String configClazzStr = moduleConfigPackage +SEPARATOR+module+"Config";
            Class configClazz = null;
            List<Field> allFieldsList = new ArrayList<Field>();
            Field allFields[] =new Field[]{};
            try{
                configClazz = Class.forName(configClazzStr);
                allFieldsList.addAll(Arrays.asList(configClazz.getDeclaredFields()));

            }catch(ClassNotFoundException cnfe){

                configClazz = HawkConfig.class;
            }

            Object instance = null;
            try {
                instance = configClazz.newInstance();
            } catch (Exception ex) {
                logger.severe("Could not create config object... Hawk is not guranteed to work properly",ex);
            }
            allFieldsList.addAll(Arrays.asList(configClazz.getFields()));
            allFields = allFieldsList.toArray(new Field[]{});

            HawkConfig instanceConfig = (HawkConfig)instance;
            instanceConfig.setModuleName(module);
            for(Field field:allFields){

                if(field.isAnnotationPresent(Config.class)){
                   String configSufix = field.getAnnotation(Config.class).value();
                   String key = configPrefix+configSufix;
                   String value = (String)props.get(key);
                   if(value != null){
                        try {

                            String type= field.getType().getName();

                            if(int.class.getName().equals(type)){
                                int valueInt = Integer.parseInt(value);
                                field.setInt(instance,valueInt);
                            }else if(long.class.getName().equals(type)){
                                long valueLong = Long.parseLong(value);
                                field.setLong(instance,valueLong);
                            }else if(String.class.getName().equals(type)){
                                field.set(instance, value);
                            }
                        } catch (Exception ex) {
                          logger.severe("Exception occurred", ex);
                      }
                  }
                   configs.put(module, instance);
                }
            }
    }






    /**
     * This cachces hawk configs from properties
     * @param props
     */
    private  void cacheHawkConfig(Properties props){

            
        String hawkModuleName = getHawkMainModule();

        String configPackage = HAWK_CONFIG_PACKAGE;

        

        this.cache
                (
                     hawkModuleName,
                     props,
                     configPackage,
                     hawkModuleName
                );
    }

    /**
     * This returns target application's main module
     * @param props containing hawk.conf key value pair
     * @return returns target application's main module
     */
    public  String getTargetMainModule(Properties props){
        
        targetMainModule = props.getProperty(HAWK_TARGET_MAIN_MODULE);
        return targetMainModule;
    }

    /**
     * This returns target application's main module
     * @return returns target application's main module
     */
    public  String getTargetMainModule(){
        return targetMainModule;
    }

    /**
     * This returns target application's module package
     * @param props containing hawk.conf key value pair
     * @return returns target application's module package
     */
    public  String getTargetModulePackage(Properties props){

        targetModulePackage = props.getProperty(TARGET_MODULE_PACKAGE_KEY);
        return targetModulePackage;
    }

    /**
     * This returns target application's module package
     * @return returns target application's module package
     */
    public  String getTargetModulePackage(){

        
        return targetModulePackage;
    }

    /**
     * Reference to target module's pacakge
     */
    private  String targetModulePackage;
    

    /**
     * Returns Hawk main module
     * @return returns hawk main module
     */
    public  String getHawkMainModule(){
        String moduleName = HAWK+SEPARATOR+MAIN_MODULE;
        return moduleName;
    }

    /**
     * Reference to target main module 
     */
    private static String targetMainModule;

    /**
     * This caches target applications's configs
     * @param props containing hawk.conf key value pair
     */
    private  void cacheTargetAppConfig(Properties props){

        String targetModuleName = getTargetMainModule(props);
        String moduleConfigPackage = props.getProperty(TARGET_MODULE_CONFIG_PACKAGE_KEY);
        getTargetModulePackage(props);
        
        this.cache
                (
                     targetModuleName,
                     props,
                     moduleConfigPackage,
                     HAWK+SEPARATOR+targetModuleName+SEPARATOR
                );

    }

    /**
     * This overrides the module execution sequence as defined in the perf.conf
     * which is provided as -D option with java
     */
    public  void overrideModulesExecutionSequence(){

        Set<HawkConfig> sortedHawkConfigs = new TreeSet<HawkConfig>();


        for(Iterator itr = configs.keySet().iterator();itr.hasNext();){
            String module = (String)itr.next();
            if(!LibraryModule.isLibraryModule(module)){
                sortedHawkConfigs.add(configs.get(module,HawkConfig.class));
            }
        }

        

        Map<String,IModule> updatedModules = new LinkedHashMap<String,IModule>();
        Map<String,IModule> cachedModules = ModuleFactory.getInstance().getCachedTargetAppModules();
        if(sortedHawkConfigs.isEmpty()){

            updatedModules = ModuleFactory.getInstance().getModules();
        }else{
            for(HawkConfig xpr:sortedHawkConfigs){
                updatedModules.put(xpr.getModuleName(), cachedModules.get(xpr.getModuleName()));
            }
        }
        
        ModuleFactory.getInstance().setCachedTargetAppModules(updatedModules);
        ModuleFactory.getInstance().cacheLibraryModules(new TreeMap<Integer,IModule>());
    }
    /**
     * This returns map containing all configs.
     * @return returns map containing all configs.
     */
    public  HawkCache getModuleConfig(){
       if(configs == null || configs.isEmpty()){
           cacheConfig();
       }
        return configs;
    }
    }




