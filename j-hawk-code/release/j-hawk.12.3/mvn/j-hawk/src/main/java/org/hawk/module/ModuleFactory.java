package org.hawk.module;

import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.Module;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import java.util.logging.Level;
import org.hawk.module.annotation.Implementor;
import org.hawk.module.annotation.Implementors;
import static org.hawk.constant.HawkConstant.*;

/**
 *
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 */
public class ModuleFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(ModuleFactory.class.getName());
    private Map<String, IModule> cachedLibraryModules = new LinkedHashMap<String, IModule>();
    private Map<String, IModule> cachedTargetAppModules = new LinkedHashMap<String, IModule>();
    private static final ModuleFactory theInstance = new ModuleFactory();

    private ModuleFactory() {
    }

    public static ModuleFactory getInstance() {
        return theInstance;
    }

    public Map<String, IModule> getCachedLibraryModules() {
        return cachedLibraryModules;
    }

    public void setCachedLibraryModules(Map<String, IModule> cachedLibraryModules) {
        this.cachedLibraryModules = cachedLibraryModules;
    }

    public Map<String, IModule> getCachedTargetAppModules() {
        return cachedTargetAppModules;
    }

    public void setCachedTargetAppModules(Map<String, IModule> cachedTargetAppModules) {
        this.cachedTargetAppModules = cachedTargetAppModules;
    }

    public String getModuleName(IModule module) {
        if (module == null) {
            return null;
        }
        return getModuleName(module.getClass());
    }

    public String getModuleName(Class moduleClazz) {
        if (moduleClazz == null) {
            return null;
        }

        String moduleName = moduleClazz.getSimpleName();
        if (moduleClazz.isAnnotationPresent(Module.class)) {

            Module moduleAnnotation = (Module) moduleClazz.getAnnotation(Module.class);
            moduleName = moduleAnnotation.name();
        }

        return moduleName;
    }

    /**
     * This caches all the Hawk modules.
     * Essentially these includes target module's IModule implementation and hawk's internal
     * library modules.
     * @param props the hawk property file.
     * @return
     */
    public Map<String, IModule> cacheModules(Properties props) {

        Map<Integer, IModule> map = new TreeMap<Integer, IModule>();
        this.cacheLibraryModules(map);
        this.cacheTargetAppModules(props, map);
        this.cacheSubTasks();
        return this.getModules();

    }

    /**
     * This caches all the hawk library modules.
     * @param map
     * @return
     */
    public boolean cacheLibraryModules() {
        Map<Integer, IModule> map = new TreeMap<Integer, IModule>();
        cacheLibraryModules(map);
        return true;

    }

    /**
     * This caches all the hawk library modules.
     * @param map
     * @return
     */
    public boolean cacheLibraryModules(Map<Integer, IModule> map) {

        Class libModuleClazz = LibraryModule.class;

        if (libModuleClazz.isAnnotationPresent(Implementors.class)) {
            Implementors implementors = (Implementors) libModuleClazz.getAnnotation(Implementors.class);

            for (Implementor implementor : implementors.value()) {
                try {
                    String clazzStr = HAWK_MODULE_LIBRARY_PACKAGE + SEPARATOR + implementor.clazz();
                    Class clazz = Class.forName(clazzStr);
                    IModule instance = (IModule) clazz.newInstance();
                    map.put(map.size(), instance);

                } catch (Exception ex) {
                    System.out.println("could not cache library modules..." + ex.getMessage());
                    logger.severe("could not cache library modules..." + ex.getMessage());
                }

            }

        }

        for (Entry<Integer, IModule> entry : map.entrySet()) {
            this.cachedLibraryModules.put(getModuleName(entry.getValue()), entry.getValue());
        }



        return true;
    }

    /**
     * This caches target module's all IModule implementations.
     * @param props
     * @param map
     * @return
     */
    private boolean cacheTargetAppModules(Properties props, Map<Integer, IModule> map) {

        for (int i = 1; i < Integer.MAX_VALUE; i++) {

            String moduleImplKey = TARGET_MODULE_IMPLEMENTOR_KEY + SEPARATOR + i;
            String clazzStr = props.getProperty(moduleImplKey);

            if (clazzStr == null || clazzStr.isEmpty()) {
                break;
            }

            String modulePackage = props.getProperty(TARGET_MODULE_PACKAGE_KEY);
            clazzStr = modulePackage + SEPARATOR + clazzStr;
            try {
                Class clazz = Class.forName(clazzStr);
                IModule instance = (IModule) clazz.newInstance();
                map.put(i + map.size() - 1, instance);
            } catch (Exception ex) {
                logger.severe("Could not create new module instance ", ex);
                return false;
            }


        }

        for (Entry<Integer, IModule> entry : map.entrySet()) {
            this.getCachedTargetAppModules().put(getModuleName(entry.getValue()), entry.getValue());
        }

        return true;
    }

    private boolean areLibraryModulesCached() {
        return this.getCachedLibraryModules() != null && !this.getCachedLibraryModules().isEmpty();
    }

    private boolean areTargetAppModulesCached() {
        return this.getCachedTargetAppModules() != null && !this.getCachedTargetAppModules().isEmpty();
    }

    /**
     * This returns all the hawk modules.
     * If the cache is empty , it fills it up.
     *
     * @param props
     * @return
     */
    public Map<String, IModule> getModules(Properties props) {
        if (!this.areLibraryModulesCached()) {
            return cacheModules(props);
        }
        return this.getModules();
    }

    /**
     * This returns the cached module.
     * This does not check for cache emptiness.
     * @return
     */
    public Map<String, IModule> getModules() {

        Map<String, IModule> result = new LinkedHashMap<String, IModule>();
        result.putAll(this.getCachedLibraryModules());
        result.putAll(this.getCachedTargetAppModules());
        return result;
    }

    public <T> T getModule(Class<T> clazz) {
        return (T) getModules().get(getModuleName(clazz));
    }

    /**
     * This caches the subtasks implemented by the user using
     * <tt>SubTask</tt>.
     * @return a map containing key as <tt>IModule</tt>
     * and value as another map which contains
     * task name and <tt>SubTaskContainer</tt>
     */
    private void cacheSubTasks() {

        Map<String, IModule> moduleMap = ModuleFactory.getInstance().getModules();
        for (Entry<String, IModule> entry : moduleMap.entrySet()) {

            IModule module = entry.getValue();
            module.cacheSubTasks();

        }
    }
}
