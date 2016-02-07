

package org.hawk.data.perf;

import org.hawk.logger.HawkLogger;
import org.hawk.module.ModuleFactory;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import org.hawk.module.IModule;
import static org.hawk.constant.HawkConstant.*;

/**
 * A data object to hold hawk perf data for plotting purpose.
 * @VERSION 1.0 6 Apr, 2010
 * @author msahu
 */
public class HawkPerfData {

     private static final HawkLogger logger = HawkLogger.getLogger(HawkPerfData.class.getName());
   
    /**
     * module name
     */
    public String module;

    /**
     * module sub task
     */
    public String moduleSubTask="NONE";

    /**
     * module execution start time
     */
    public String moduleStartTime;

    /**
     * module execution end time
     */
    public String moduleEndTime;


    /**
     * module execution start time
     */
    public long moduleStartTimeLong;

    /**
     * module execution end time
     */
    public long moduleEndTimeLong;

    /**
     * module execution status
     * @see ModuleExcecutionStatusEnum
     */
    public String moduleExecutionStatus;

    /**
     * module execution time.
     * This is essentially difference between
     * <tt>moduleEndTime</tt> and <tt>moduleStartTime</tt
     */
    public long moduleExecutionTime;


    /**
     * summation module execution time.
     */
    public long sigmaModuleExecTime;

    public String getModuleEndTime() {
        return moduleEndTime;
    }

    public void setModuleEndTime(String moduleEndTime) {

        this.moduleEndTime = moduleEndTime;

        try {

            this.moduleEndTimeLong = FORMATTER.parse(this.moduleEndTime).getTime();

        } catch (ParseException ex) {

            logger.severe(ex);

        }

    }

    public long getModuleEndTimeLong() {
        return moduleEndTimeLong;
    }

    public void setModuleEndTimeLong(long moduleEndTimeLong) {
        this.moduleEndTimeLong = moduleEndTimeLong;
    }

    public String getModuleStartTime() {
        return moduleStartTime;
    }

    public void setModuleStartTime(String moduleStartTime) {

        this.moduleStartTime = moduleStartTime;

        try {

            this.moduleStartTimeLong = FORMATTER.parse(this.moduleStartTime).getTime();

        } catch (ParseException ex) {

            logger.severe(ex);

        }
    }

    public long getModuleStartTimeLong() {
        return moduleStartTimeLong;
    }


    public void setModuleStartTimeLong(long moduleStartTimeLong) {
        this.moduleStartTimeLong = moduleStartTimeLong;
    }


    

    /**
     * This is to indicate task execution started.
     * @param m module
     * @param subTask subtask
     * @return <tt>true</true> for valid module otherwise <tt>false</tt>.
     */
    public boolean start(IModule m,String subTask){

        if(m != null){

            this.module = ModuleFactory.getInstance().getModuleName(m);

            this.moduleSubTask = subTask;

            this.moduleStartTimeLong = System.currentTimeMillis();

            this.moduleStartTime = FORMATTER.format(new Date(this.moduleStartTimeLong));

            return true;

        }

        return false;

    }

    /**
     * This is to indicate task execution ended successfully.
     * @param m module
     * @param subTask subtask
     * @return <tt>true</true> for valid module otherwise <tt>false</tt>.
     */
    public boolean end(IModule m,String subTask){

        if(m!= null && this.module.equals(ModuleFactory.getInstance().getModuleName(m)) && this.moduleSubTask.equals(subTask)){

            this.moduleEndTimeLong = System.currentTimeMillis();

            this.moduleEndTime = FORMATTER.format(new Date(this.moduleEndTimeLong));

            this.moduleExecutionTime = this.moduleEndTimeLong-this.moduleStartTimeLong;

            this.moduleExecutionStatus = ModuleExcecutionStatusEnum.SUCCESS.name();

            return true;

        }

        return false;
    }
    
    /**
     * This is to indicate task execution ended with failure.
     * @param m module
     * @param subTask subtask
     * @return <tt>true</true> for valid module otherwise <tt>false</tt>.
     */
    public boolean endWithFailure(IModule m,String subTask){

        if(m!= null){

            this.end(m,subTask);

            this.moduleExecutionStatus = ModuleExcecutionStatusEnum.FAILURE.name();

            return true;

        }

        return false;
    }

    @Override
    public String toString(){
        return this.getData();
    }


    /**
     * module's data as it is stored in output file for plotting.
     * @return
     */
    public String getData(){
        return  this.getModule() +"-"+

                this.getModuleSubTask()+HAWK_PERF_DATA_SEPRATOR+

                this.getModuleStartTime()+HAWK_PERF_DATA_SEPRATOR+

                this.getModuleEndTime()+HAWK_PERF_DATA_SEPRATOR+

                this.getModuleExecutionTime()+HAWK_PERF_DATA_SEPRATOR+

                this.getModuleExecutionStatus()+

                "\n";

    }

   

    public String getModule() {
        return module;
    }

    public String getModuleSubTaskName(){
        return this.getModule()+"-"+this.getModuleSubTask();
    }

    public void setModule(String module) {
        this.module = module;
    }

    
    public String getModuleExecutionStatus() {
        return moduleExecutionStatus;
    }

    public void setModuleExecutionStatus(String moduleExecutionStatus) {
        this.moduleExecutionStatus = moduleExecutionStatus;
    }

    public long getModuleExecutionTime() {
        return moduleExecutionTime;
    }

    public void setModuleExecutionTime(long moduleExecutionTime) {
        this.moduleExecutionTime = moduleExecutionTime;
    }

    

    public String getModuleSubTask() {
        return moduleSubTask;
    }

    public void setModuleSubTask(String moduleSubTask) {
        this.moduleSubTask = moduleSubTask;
    }


    


    public enum PerfDataCmpEnum{

        SAME_TASK_SAME_START_TIME,

        SAME_TASK_LESS_START_TIME,

        SAME_TASK_HIGHER_START_TIME,

        DIFF_TASK_SAME_START_TIME,

        DIFF_TASK_LESS_START_TIME,

        DIFF_TASK_HIGHER_START_TIME,

        DIFF_TASK
        

    }
    public PerfDataCmpEnum compare(HawkPerfData other){
       
        if (other == null) {
            return null;
        }


        if ((this.module == null) ? (other.module != null) : !this.module.equals(other.module)) {

            return PerfDataCmpEnum.DIFF_TASK;

        }

        if ((this.moduleSubTask == null) ? (other.moduleSubTask != null) : !this.moduleSubTask.equals(other.moduleSubTask)) {

            return PerfDataCmpEnum.DIFF_TASK;

        }

        if ((this.moduleStartTime == null) ? (other.moduleStartTime != null) : !this.moduleStartTime.equals(other.moduleStartTime)) {

            return PerfDataCmpEnum.DIFF_TASK;

        }

        PerfDataCmpEnum status = null;

        if (this.moduleExecutionTime < other.getModuleExecutionTime()) {

            status = PerfDataCmpEnum.SAME_TASK_LESS_START_TIME;

        }else if (this.moduleExecutionTime > other.getModuleExecutionTime()) {

            status = PerfDataCmpEnum.SAME_TASK_HIGHER_START_TIME;

        }else{

            status = PerfDataCmpEnum.SAME_TASK_SAME_START_TIME;

        }

        return status;
        
    }

   






}




