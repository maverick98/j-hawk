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
