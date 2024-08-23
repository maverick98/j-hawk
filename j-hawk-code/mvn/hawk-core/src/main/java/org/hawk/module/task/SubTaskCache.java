/**
 *  
 * left.
 *
 *    
 *  
 *
 */
package org.hawk.module.task;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import org.common.di.AppContainer;
import static org.hawk.constant.HawkConstant.HAWK;
import static org.hawk.constant.HawkConstant.HAWK_EXECUTION_PARAMS;
import static org.hawk.constant.HawkConstant.SEPARATOR;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.core.ICoreModule;
import org.hawk.module.plugin.IPluginModule;
import org.hawk.module.properties.HawkModulePropertiesManager;

/**
 *
 * @author msahu98
 */
public class SubTaskCache implements ISubTaskCache {

    @Override
    public boolean cacheSubTasks(IModule module) throws Exception {
        if (module == null) {
            return false;
        }

        Properties hawkProperties = AppContainer.getInstance().getBean(HawkModulePropertiesManager.class).getHawkProperties();

        String targetApp = AppContainer.getInstance().getBean(HawkModulePropertiesManager.class).getTargetApp();

        Class moduleClazz = module.getClass();

        Method methods[] = moduleClazz.getDeclaredMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(SubTask.class)) {

                SubTask subTaskAnnoation = (SubTask) method.getAnnotation(SubTask.class);

                SubTaskContainer subTaskContainer = new SubTaskContainer();

                subTaskContainer.setTaskName(subTaskAnnoation.name().trim());

                subTaskContainer.setSequence(subTaskAnnoation.sequence());

                subTaskContainer.setTaskMethod(method);

                subTaskContainer.setModule(module);

                subTaskContainer.setIgnoreException(subTaskAnnoation.ignoreException());

                subTaskContainer.setHawkParam(subTaskAnnoation.hawkParam());

                if (hawkProperties != null) {

                    StringBuilder sb = new StringBuilder(HAWK);

                    sb.append(SEPARATOR);

                    sb.append(targetApp);

                    sb.append(SEPARATOR);

                    sb.append(this.getClass().getSimpleName());

                    sb.append(SEPARATOR);

                    sb.append(subTaskContainer.getTaskName());

                    sb.append(SEPARATOR);

                    sb.append(HAWK_EXECUTION_PARAMS);

                    String hawkExecutionParams = hawkProperties.getProperty(sb.toString());

                    subTaskContainer.setHawkExecutionParams(hawkExecutionParams);

                }

                module.getSubTasks().put(subTaskContainer.getTaskName(), subTaskContainer);

            }

        }

        return true;
    }

    @Override
    public boolean cacheSubTasks(Map<String, IModule> moduleMap) throws Exception {

        // Map<String, IModule> moduleMap = this.getAllModules();
        for (Map.Entry<String, IModule> entry : moduleMap.entrySet()) {
            this.cacheSubTasks(entry.getValue());
        }
        return true;
    }
    /*
     @Override
     public boolean cacheCoreSubTasks(Map<String, ICoreModule> moduleMap) throws Exception {
     //Map<String, ICoreModule> moduleMap = this.getCoreModules();

     for (Map.Entry<String, ICoreModule> entry : moduleMap.entrySet()) {
     this.cacheSubTasks(entry.getValue());
     }
     return true;
     }

     @Override
     public boolean cachePluginSubTasks(Map<String, IPluginModule> moduleMap) throws Exception {
     //Map<String, IPluginModule> moduleMap = this.getPluginModules();
     for (Map.Entry<String, IPluginModule> entry : moduleMap.entrySet()) {
     this.cacheSubTasks(entry.getValue());
     }
     return true;
     }
     */

    /**
     * displays all the tasks implemented by the user on the console.
     *
     * @return
     */
    @Override
    public String showTasks(Map<String, IModule> moduleMap) {
        StringBuilder result = new StringBuilder();
        String space = "                     ";
        String space1 = "       ";
        // Map<String, IModule> moduleMap = this.getAllModules();
        for (Map.Entry<String, IModule> entry : moduleMap.entrySet()) {

            result.append(space);
            result.append(entry.getKey());
            result.append("\n");
            result.append(space);
            result.append(space1);
            result.append("|");
            result.append("\n");
            result.append(space);
            result.append(space1);
            result.append("|");
            result.append("\n");

            Map<String, SubTaskContainer> map = entry.getValue().getSubTasks();

            for (Map.Entry<String, SubTaskContainer> entry1 : map.entrySet()) {

                result.append(space);
                result.append(space1);
                result.append("|------->");
                result.append(entry1.getValue().getTaskSignature());
                result.append("\n");
            }

            result.append("\n");
            result.append("\n");

        }
        System.out.println(result.toString());
        logger.info(result.toString());
        return result.toString();

    }
    private static final HawkLogger logger = HawkLogger.getLogger(SubTaskCache.class.getName());
}
