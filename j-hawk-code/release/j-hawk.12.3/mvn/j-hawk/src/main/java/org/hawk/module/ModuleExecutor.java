

package org.hawk.module;


import org.hawk.data.perf.plotter.HawkPlotter;
import org.hawk.module.script.ScriptUsage;
import org.hawk.shutdown.HawkShutdown;
import org.hawk.cache.HawkCache;
import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.exception.HawkException;
import org.hawk.module.config.HawkConfig;
import org.hawk.module.config.HawkConfigManager;
import org.hawk.startup.HawkStartup;
import org.hawk.util.HawkUtil;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.hawk.logger.HawkLogger;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.module.script.type.Variable;
import org.hawk.module.task.SubTaskContainer;
import static org.hawk.constant.HawkConstant.*;
/**
 *
 * @VERSION 1.0 30 Mar, 2010
 * @author msahu
 */
public class ModuleExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ModuleExecutor.class.getName());

    /**
     * Creates the singleton instance of <tt>ModuleExecutor</tt>
     */
    private static final ModuleExecutor moduleExecutor = new ModuleExecutor();

    /**
     * Private CTOR of <tt>ModuleExecutor</tt>
     */
    private ModuleExecutor(){

    }

   /**
     * SingleTon accessor method of <tt>ModuleExecutor</tt>
     * @return
     */
    public static ModuleExecutor getInstance(){
        return moduleExecutor;
    }



    /**
     * main method when hawk is run in property mode.
     * @param args
     */
    public static void main(String args[]) throws HawkException{

        Runtime.getRuntime().addShutdownHook(new Thread(new HawkShutdown()));

        ScriptUsage.getInstance().setBuildMode(BuildModeEnum.PERF);

        HawkStartup.getInstance().startUp();

        try{
            
            ModuleExecutor.getInstance().execute();

        }catch(Exception ex){

            System.out.println("error while executing modules"+ex.getMessage());

            logger.severe("error while executing modules"+ex.getMessage());

        }

    }

    /**
     * This is the entry point when hawk is run in property mode.
     * @return
     * @throws HawkException
     */
    public  boolean execute() throws HawkException{

        HawkCache configs = HawkConfigManager.getInstance().getModuleConfig();

        HawkConfig hawkConfig = configs.get(HAWK_MAIN_MODULE,HawkConfig.class);

        int hawkIteration = hawkConfig.getIteration();

        long hawkThinkTime = hawkConfig.getIterationThinkTime();

        long durationTime = hawkConfig.getDuration();
       

        boolean forever = false;

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

                for(Entry<String,IModule>entry:ModuleFactory.getInstance().getCachedTargetAppModules().entrySet()){

                    executeModule(entry.getValue());

                }

                logger.info("Iteration {"+i+"} over...");

                boolean stop = shouldHawkStop(endDate);

                if(stop){

                    logger.info("----------------FINISHED----------------------Due to timeout");

                    finishHawk();

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

        return finishHawk();
    }


    /**
     * This flushes all the in-memory performance data to the perf file
     * and then starts processing it.
     * @return returns true on success.
     * @see HawkPlotter
     */
    public  boolean finishHawk(){

        HawkPerfDataCollector.dump(true);

        boolean status =  HawkPerfDataCollector.processPerfData();

        System.exit(status?0:1);

        return status;

    }

    /**
     * This checks if the hawk needs to be stopped.
     * 
     * @param endDate
     * @return
     */
    private  boolean shouldHawkStop(Date endDate){

        long currentTime = System.currentTimeMillis();

        Date currentDate  = new Date(currentTime);
        
        return currentDate.compareTo(endDate)>=0;
    }

    /**
     * This executes the input module
     * @param module input module to be executed.
     * @return returns true on success
     * @throws HawkException
     */
    private  boolean executeModule(IModule module) throws HawkException{

        Map<String,HawkConfig> configs = HawkConfigManager.getInstance().getModuleConfig();

        String moduleName = ModuleFactory.getInstance().getModuleName(module);

        HawkConfig moduleConfig = configs.get(moduleName);

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

    /**
     * This executes the sorted tasks.
     * @param sortedTasks input sorted tasks based on their sequence.
     * @return returns true on success
     * @throws HawkException
     */
    private  boolean executeTasks(Set<SubTaskContainer> sortedTasks) throws HawkException{

       
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
     * @throws HawkException
     */
    public  Variable executeModule(IModule module,String subTask) throws HawkException{

        Variable status = null;

        if(module == null || subTask == null || subTask.isEmpty()){

            throw new HawkException("Invalid module or subtask");

        }
        Set<SubTaskContainer> sortedTasks = module.getSortedSubTasks();


        SubTaskContainer subTaskContainer = null;

        for(SubTaskContainer stc:sortedTasks){

            if(stc.getTaskName().equals(subTask)){

                subTaskContainer = stc;

                break;

            }

        }

        String moduleName = ModuleFactory.getInstance().getModuleName(module);

        if(subTaskContainer == null){

            throw new HawkException("No Such Task {"+subTask+"} defined in {"+moduleName+"}");

        }

        status  = subTaskContainer.execute().getVariableValue();

        return status;
    }
}




