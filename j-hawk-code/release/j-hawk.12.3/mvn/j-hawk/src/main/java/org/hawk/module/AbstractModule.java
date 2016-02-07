package org.hawk.module;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.config.HawkConfigManager;
import org.hawk.module.task.SubTaskContainer;
import static org.hawk.constant.HawkConstant.*;

/**
 *
 * @author manoranjan
 */
public abstract class AbstractModule implements IModule {

    /**
     * A map containing key as <tt>IModule</tt>
     * and value as another map which contains
     * task name and <tt>SubTaskContainer</tt>
     * @see SubTaskContainer
     * @see IModule
     */
    private Map<String, SubTaskContainer> cachedSubTasks;

    protected AbstractModule() {

        this.cachedSubTasks = new HashMap<String, SubTaskContainer>();
        
    }

    /**
     * The target application's main module needs to implement it
     * for its initialization.
     * @return
     */
    @Override
    public abstract boolean startUp();

    /**
     * This resets one cycle of execution.
     * This is useful when hawk is run in property mode.
     * @return
     */
    @Override
    public abstract boolean reset();

    /**
     * Module encapsulates the subtasks
     * @return
     */
    @Override
    public Map<String, SubTaskContainer> getSubTasks() {
        if (this.cachedSubTasks != null && !this.cachedSubTasks.isEmpty()) {
            return this.cachedSubTasks;
        }
        return this.cacheSubTasks();
    }

    @Override
    public Map<String, SubTaskContainer> cacheSubTasks() {

        
        Properties hawkProperties = HawkConfigManager.getInstance().getHawkProperties();

        String targetApp = HawkConfigManager.getInstance().getTargetApp(hawkProperties);

        Class moduleClazz = this.getClass();

        Method methods[] = moduleClazz.getDeclaredMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(SubTask.class)) {

                SubTask subTaskAnnoation = (SubTask) method.getAnnotation(SubTask.class);


                SubTaskContainer subTaskContainer = new SubTaskContainer();

                subTaskContainer.setTaskName(subTaskAnnoation.name());

                subTaskContainer.setSequence(subTaskAnnoation.sequence());

                subTaskContainer.setTaskMethod(method);

                subTaskContainer.setModule(this);

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

                //System.out.println("putting {"+subTaskContainer.getTaskName()+"} in map {"+this.cachedSubTasks+"}");

                this.cachedSubTasks.put(subTaskContainer.getTaskName(), subTaskContainer);

            }

        }

        return this.cachedSubTasks;
    }

    /**
     * This returns {@link Set<SubTaskContainer>} the subtasks of a module sorted on the sequence number.
     * @param module for which sorted tasks are required.
     * @return returns {@link Set<SubTaskContainer>} the subtasks of a module sorted on the sequence number.
     * @throws org.hawk.exception.HawkException
     */
    @Override
    public Set<SubTaskContainer> getSortedSubTasks() {


        Map<String, SubTaskContainer> subTasks = this.getSubTasks();

        if (subTasks == null || subTasks.isEmpty()) {

            return null;

        }

        Set<SubTaskContainer> sortedTasks = new TreeSet<SubTaskContainer>();

        for (Entry<String, SubTaskContainer> entry : subTasks.entrySet()) {

            sortedTasks.add(entry.getValue());
        }

        return sortedTasks;
    }

    @Override
    public SubTaskContainer getSubTask(String subTaskName) {
        return this.getSubTasks().get(subTaskName);
    }
}
