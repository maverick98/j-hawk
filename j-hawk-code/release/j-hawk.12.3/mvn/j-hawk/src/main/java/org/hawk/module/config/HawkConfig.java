

package org.hawk.module.config;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.hawk.module.annotation.Config;
import static org.hawk.constant.HawkConstant.*;
/**
 * This class represents the configuration
 * defined in hawk property file.
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 * @see HawkConfigManager
 */
public class HawkConfig implements Comparable{

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

    public int compareTo(Object o) {
        if(o == null || !(o instanceof HawkConfig)){
            return -1;
        }

        HawkConfig thatConfig = (HawkConfig)o;
        return this.getSequence()-thatConfig.getSequence();
    }

    

}




