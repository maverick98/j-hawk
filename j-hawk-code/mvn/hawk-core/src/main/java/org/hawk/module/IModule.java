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

package org.hawk.module;

import java.util.Map;
import java.util.Set;
import org.hawk.module.task.SubTaskContainer;

/**
 * This represents hawk module interface.
 * Target application's main module need to implement startup
 * for its initialization.
 * @author msahu
 * @see HawkCoreModule
 *
 */

public interface  IModule {
     /**
      * The target application's main module needs to implement it
      * for its initialization.
      * @return
      */
     boolean startUp();
    
     /**
      * This resets one cycle of execution.
      * This is useful when hawk is run in property mode.
      * @return
      */
     boolean reset();
     
     
     public String getName();
    

     /**
      * Module encapsulates the subtasks
      * @return
      */
     Map<String,SubTaskContainer> getSubTasks();
    
     Set<SubTaskContainer> getSortedSubTasks();

     SubTaskContainer getSubTask(String subTaskName);
}
