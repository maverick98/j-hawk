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

package org.hawk.module.properties;

import static org.hawk.constant.HawkConstant.HAWK;
import static org.hawk.constant.HawkConstant.SEPARATOR;
import org.hawk.module.annotation.Config;
/**
 * This class represents the configuration
 * defined in hawk property file.
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 * @see HawkConfigManager
 */
public class HawkModuleProperties implements Comparable{

    /**
     * sequence of the module that defines its execution order.
     */
    @Config("sequence")
    public int sequence =1;

    /**
     * This determines how many times the module is to be executed.
     */
    @Config("iteration")
    public int iteration =1;

    /**
     * This indicates how much time hawk to sleep between iteration.
     */
    @Config("iteration.thinktime")
    public long iterationThinkTime=0;

    /**
     * Hawk will sleep this much time after one complete cycle.
     */
    @Config("thinktime")
    public long thinkTime=0;

    /**
     * A switch for the user to control running of a module.
     */
    @Config("runnable")
    public int runnable=1;

    /**
     * Total duration of  hawk.
     */
    @Config("duration")
    public long duration=1;

    /**
     * This is used to pass params to hawk program when run in property mode
     */
    @Config("params")
    public String params;
    

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    

    private String moduleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getRunnable() {
        return runnable;
    }

    public void setRunnable(int runnable) {
        this.runnable = runnable;
    }

    public boolean shouldRun(){
        return this.runnable !=0;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public long getIterationThinkTime() {
        return iterationThinkTime;
    }

    public void setIterationThinkTime(long iterationThinkTime) {
        this.iterationThinkTime = iterationThinkTime;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getThinkTime() {
        return thinkTime;
    }

    public void setThinkTime(long thinkTime) {
        this.thinkTime = thinkTime;
    }

    public String fetchConfig(String annotation,Class configClazz,Object defaultValue){
        String key = HAWK+SEPARATOR+configClazz.getSimpleName()+SEPARATOR+annotation;
        System.out.println("key name is {"+key+"}");
        System.out.println("default value is {"+defaultValue+"}");
        return System.getProperty(key, String.valueOf(defaultValue));
    }

    @Override
    public String toString() {
        return " sequence = {"+this.sequence+"} , iteration={"+this.iteration+"}, " +
               " iterationTT={"+this.iterationThinkTime+"} , TT={"+this.thinkTime+"} , duration={"+this.duration+"}\n";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.sequence;
        hash = 53 * hash + (this.params != null ? this.params.hashCode() : 0);
        hash = 53 * hash + (this.moduleName != null ? this.moduleName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HawkModuleProperties other = (HawkModuleProperties) obj;
        if (this.sequence != other.sequence) {
            return false;
        }
        if ((this.params == null) ? (other.params != null) : !this.params.equals(other.params)) {
            return false;
        }
        if ((this.moduleName == null) ? (other.moduleName != null) : !this.moduleName.equals(other.moduleName)) {
            return false;
        }
        return true;
    }

    
    @Override
    public int compareTo(Object o) {
        if(o == null || !(o instanceof HawkModuleProperties)){
            return -1;
        }

        HawkModuleProperties thatConfig = (HawkModuleProperties)o;
        return this.getSequence()-thatConfig.getSequence();
    }

    

}




