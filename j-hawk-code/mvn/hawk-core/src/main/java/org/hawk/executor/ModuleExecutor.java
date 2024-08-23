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

package org.hawk.executor;


import java.util.Set;
import org.hawk.data.perf.PerfDataProcessor;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkCommandParser;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;
//import org.hawk.module.ModuleCache;
import org.hawk.module.task.SubTaskContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 */
   
   
public class ModuleExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ModuleExecutor.class.getName());


  //  @Autowired(required = true)
       
   // ModuleCache moduleFactory;

    @Autowired(required = true)
       
    HawkCommandParser hawkUsage;

    public HawkCommandParser getHawkUsage() {
        return hawkUsage;
    }

    public void setHawkUsage(HawkCommandParser hawkUsage) {
        this.hawkUsage = hawkUsage;
    }

    
    
    /**
     * main method when hawk is run in property mode.
     * @param args
     */
    /*
    public static void main(String args[]) throws Exception{

        
       // HawkSetup.getInstance().configure();
        ModuleExecutor moduleExecutor = AppContainer.getInstance().getBean(MODULEEXECUTOR,ModuleExecutor.class);
        
        
        try{
            
            moduleExecutor.execute();

        }catch(HawkStopException ex){

            

            logger.error("Stopping the hawk");

        }catch(Exception ex){

            

            logger.error("error while executing modules"+ex.getMessage());

        }

    }
    */
    /**
     * This is the entry point when hawk is run in property mode.
     * @return
     * @throws Exception
     */
    /*
    public  boolean execute() throws Exception{
        //Property mode is disabled now   
        HawkCache configs = null ;//AppContainer.getInstance().getBean(HAWKMODULEPROPERTIESMANAGER,HawkModulePropertiesManager.class).getModuleConfig();

        HawkModuleProperties hawkConfig = configs.get(HAWK_MAIN_MODULE,HawkModuleProperties.class);

        int hawkIteration = hawkConfig.getIteration();

        long hawkThinkTime = hawkConfig.getIterationThinkTime();

        long durationTime = hawkConfig.getDuration();
       

        boolean forever;

        if(durationTime == -1){

            //this means run for ever...

            forever = true;

        }else{

            forever = false;

        }

        long startTime = System.currentTimeMillis();

        logger.info("current time "+new Date(startTime));

        logger.info("duration "+durationTime*1000);

        long endTime = startTime+(durationTime*1000);

        Date endDate = new Date(endTime);


        logger.info("Hawk will end at {"+endDate+"}");

        do{

            for(int i=1; i<=hawkIteration;i++){

                for(Entry<String,IModule>entry:moduleFactory.getPluginModules().entrySet()){

                    executeModule(entry.getValue());

                }

                logger.info("Iteration {"+i+"} over...");

                boolean stop = shouldHawkStop(endDate);

                if(stop){

                    logger.info("----------------FINISHED----------------------Due to timeout");

                    dumpPerfData();
                    
                    throw new HawkStopException("stop the hawk");

                }

                if(i ==hawkIteration){

                    break;

                }else{

                    logger.info("Sleeping mainmodule think time {"+hawkThinkTime+"}");

                    HawkUtil.sleep(hawkThinkTime);
                
                }

            }

        }while(forever);

        logger.info("----------------FINISHED----------------------");

        return dumpPerfData();
    }

    */  
    /**
     * This flushes all the in-memory performance data to the perf file
     * and then starts processing it.
     * @return returns true on success.
     * @see HawkPlotter
     */
    public  boolean dumpPerfData(){
        PerfDataProcessor hawkPerfDataCollector =AppContainer.getInstance().getBean( PerfDataProcessor.class);
        hawkPerfDataCollector.dump(true);

        boolean status =  hawkPerfDataCollector.processPerfData();
        return status;

    }

    /**
     * This checks if the hawk needs to be stopped.
     * 
     * @param endDate
     * @return
     */
    /*
    private  boolean shouldHawkStop(Date endDate){

        long currentTime = System.currentTimeMillis();

        Date currentDate  = new Date(currentTime);
        
        return currentDate.compareTo(endDate)>=0;
    }
    */ 

    /**
     * This executes the input module
     * @param module input module to be executed.
     * @return returns true on success
     * @throws Exception
     */
    /*
    private  boolean executeModule(IModule module) throws Exception{

        //TODO property mode is disabled now
        Map<String,HawkModuleProperties> configs = null ;//AppContainer.getInstance().getBean("hawkConfigManager",HawkModulePropertiesManager.class).getModuleConfig();

        String moduleName = module.getName();

        HawkModuleProperties moduleConfig = configs.get(moduleName);

        if(!moduleConfig.shouldRun()){

            logger.info("Not running {"+moduleName+"}");

            return false;

        }

        for(int iteraration = 1 ; iteraration <=moduleConfig.getIteration();iteraration++){

            logger.info("Executing {"+moduleName+"}");

            System.out.println("Executing {"+moduleName+"}");

            Set<SubTaskContainer> sortedTasks = module.getSortedSubTasks();

            System.out.println("tasks are  {"+sortedTasks+"}");

            executeTasks(sortedTasks);

            if(iteraration ==moduleConfig.getIteration()){

                break;

            }else{

                logger.info("Sleeping for iteration think time {"+moduleConfig.getIterationThinkTime()+"}");

                HawkUtil.sleep(moduleConfig.getIterationThinkTime());
            
            }

        }

        logger.info("Sleeping for module think time {"+moduleConfig.getThinkTime()+"}");

        HawkUtil.sleep(moduleConfig.getThinkTime());

        logger.info("Finished...");

        return true;
    }
    */
    /**
     * This executes the sorted tasks.
     * @param sortedTasks input sorted tasks based on their sequence.
     * @return returns true on success
     * @throws Exception
     */
    private  boolean executeTasks(Set<SubTaskContainer> sortedTasks) throws Exception{

       
        for(SubTaskContainer task:sortedTasks){

            task.execute();

        }

        return true;
    }

    /**
     * This executes the subtask of a module.
     * @param module the module containing subtask
     * @param subTask subtask to be executed.
     * @return returns result as hawk 
     * @throws Exception
     */
    public  Variable executeModule(IModule module,String subTask) throws Exception{

        Variable status = null;

        if(module == null || subTask == null || subTask.isEmpty()){

            throw new Exception("Invalid module or subtask");

        }
        Set<SubTaskContainer> sortedTasks = module.getSortedSubTasks();


        SubTaskContainer subTaskContainer = null;

        for(SubTaskContainer stc:sortedTasks){

            if(stc.getTaskName().equals(subTask)){

                subTaskContainer = stc;

                break;

            }

        }

        String moduleName =module.getName();

        if(subTaskContainer == null){

            throw new Exception("No Such Task {"+subTask+"} defined in {"+moduleName+"}");

        }

        status  = subTaskContainer.execute().getVariableValue();

        return status;
    }
}




