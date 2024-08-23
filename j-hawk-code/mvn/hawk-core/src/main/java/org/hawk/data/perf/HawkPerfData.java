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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hawk.constant.HawkConstant.FORMATTER;
import static org.hawk.constant.HawkConstant.HAWK_PERF_DATA_SEPRATOR;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;

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
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            this.moduleEndTimeLong = formatter.parse(this.moduleEndTime).getTime();

        } catch (ParseException ex) {

            logger.error(ex);

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
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            this.moduleStartTimeLong = formatter.parse(this.moduleStartTime).getTime();

        } catch (ParseException ex) {

            logger.error(ex);

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

        return this.start(m.getName(), subTask);

    }
    /**
     * This is to indicate task execution started.
     * @param m module
     * @param subTask subtask
     * @return <tt>true</true> for valid module otherwise <tt>false</tt>.
     */
    public boolean start(String moduleName,String subTask){

        if(moduleName != null){

            this.module = moduleName;

            this.moduleSubTask = subTask;

            this.moduleStartTimeLong = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            this.moduleStartTime = formatter.format(new Date(this.moduleStartTimeLong));

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

       return this.end(m.getName(), subTask);
    }

        /**
     * This is to indicate task execution ended successfully.
     * @param m module
     * @param subTask subtask
     * @return <tt>true</true> for valid module otherwise <tt>false</tt>.
     */
    public boolean end(String moduleName,String subTask){

        if(moduleName!= null && this.module.equals(moduleName) && this.moduleSubTask.equals(subTask)){

            this.moduleEndTimeLong = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
            this.moduleEndTime = formatter.format(new Date(this.moduleEndTimeLong));

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

    /**
     * This is to indicate task execution ended with failure.
     * @param m module
     * @param subTask subtask
     * @return <tt>true</true> for valid module otherwise <tt>false</tt>.
     */
    public boolean endWithFailure(String moduleName,String subTask){

        if(moduleName!= null){

            this.end(moduleName,subTask);

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
        StringBuilder sb = new StringBuilder();
        sb.append(this.getModule());
        sb.append ("-");

        sb.append (this.getModuleSubTask());
        sb.append(HAWK_PERF_DATA_SEPRATOR);

        sb.append(this.getModuleStartTime());
        sb.append(HAWK_PERF_DATA_SEPRATOR);

        sb.append(this.getModuleEndTime());
        sb.append(HAWK_PERF_DATA_SEPRATOR);

        sb.append(this.getModuleExecutionTime());
        sb.append(HAWK_PERF_DATA_SEPRATOR);

        sb.append(this.getModuleExecutionStatus());

        sb.append("\n");
        return sb.toString();

    }

   

    public String getModule() {
        return module;
    }

    public String getModuleSubTaskName(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getModule());
        sb.append("-");
        sb.append(this.getModuleSubTask());
        return sb.toString();
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

        PerfDataCmpEnum status;

        if (this.moduleExecutionTime < other.getModuleExecutionTime()) {

            status = PerfDataCmpEnum.SAME_TASK_LESS_START_TIME;

        }else if (this.moduleExecutionTime > other.getModuleExecutionTime()) {

            status = PerfDataCmpEnum.SAME_TASK_HIGHER_START_TIME;

        }else{

            status = PerfDataCmpEnum.SAME_TASK_SAME_START_TIME;

        }

        return status;
        
    }

    public static  Map<String, List<HawkPerfData>> groupByModule(List<HawkPerfData> all){
        
        Map<String, List<HawkPerfData>> moduleMap = new HashMap<>();

        all.stream().forEach((perfData) -> {
            List<HawkPerfData> moduleData = moduleMap.get(perfData.getModule());

            if (moduleData == null) {

                moduleData = new ArrayList<>();

            }

            moduleData.add(perfData);

            moduleMap.put(perfData.getModule(), moduleData);
         });
        return moduleMap;
    }
    
    public static  Map<String, List<HawkPerfData>> groupByModuleSubTask(List<HawkPerfData> all){
        Map<String, List<HawkPerfData>> moduleTaskMap = new HashMap<>();
        
        all.stream().forEach((perfData) -> {
            List<HawkPerfData> moduleData = moduleTaskMap.get(perfData.getModuleSubTaskName());

            if (moduleData == null) {

                moduleData = new ArrayList<>();

            }

            moduleData.add(perfData);
            
            moduleTaskMap.put(perfData.getModuleSubTaskName(), moduleData);
         });
        
        return moduleTaskMap;
    }
    






}




