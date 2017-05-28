
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
package org.hawk.module;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.hawk.module.task.SubTaskContainer;

/**
 *
 * @author manoranjan
 */
public abstract class AbstractModule implements IModule {

   
   
    /**
     * A map containing key as <tt>IModule</tt>
     * and value as another map which contains task name and
     * <tt>SubTaskContainer</tt>
     *
     * @see SubTaskContainer
     * @see IModule
     */
    private final Map<String, SubTaskContainer> cachedSubTasks;

    protected AbstractModule() {

        this.cachedSubTasks = new HashMap<>();

    }

    /**
     * The target application's main module needs to implement it for its
     * initialization.
     *
     * @return
     */
    @Override
    public abstract boolean startUp();

    /**
     * This resets one cycle of execution. This is useful when hawk is run in
     * property mode.
     *
     * @return
     */
    @Override
    public abstract boolean reset();

    /**
     * Module encapsulates the subtasks
     *
     * @return
     */
    @Override
    public Map<String, SubTaskContainer> getSubTasks() {
        return this.cachedSubTasks;
    }

    /**
     * This returns {@link Set<SubTaskContainer>} the subtasks of a module
     * sorted on the sequence number.
     *
     * @param module for which sorted tasks are required.
     * @return returns {@link Set<SubTaskContainer>} the subtasks of a module
     * sorted on the sequence number.
     * @throws org.hawk.exception.Exception
     */
    @Override
    public Set<SubTaskContainer> getSortedSubTasks() {


        Map<String, SubTaskContainer> subTasks = this.getSubTasks();

        if (subTasks == null || subTasks.isEmpty()) {

            return null;

        }

        Set<SubTaskContainer> sortedTasks = new TreeSet<>();

        subTasks.entrySet().stream().forEach((entry) -> {
            sortedTasks.add(entry.getValue());
        });

        return sortedTasks;
    }

    @Override
    public SubTaskContainer getSubTask(String subTaskName) {
        
        return this.getSubTasks().get(subTaskName);
    }

  

    @Override
    public abstract String getName();
}
