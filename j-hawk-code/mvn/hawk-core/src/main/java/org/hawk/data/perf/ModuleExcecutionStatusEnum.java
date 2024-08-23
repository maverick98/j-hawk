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


package org.hawk.data.perf;

/**
 * This denotes the module's execution status.
 * Normally if subtask excutes with exception, then
 * FAILURE is considered as its status. otherwise
 * SUCCESS.
 * @version 1.0 6 Apr, 2010
 * @author msahu
 */
public enum ModuleExcecutionStatusEnum {

    /**
     * Module that executed without exception.
     */
    SUCCESS,
    /**
     * Module that executed with exception.
     */
    FAILURE
}




