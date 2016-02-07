


package org.hawk.module.script;

import org.hawk.exception.HawkException;
import org.hawk.module.IModule;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.module.task.SubTaskContainer;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import org.hawk.module.ModuleFactory;
import org.hawk.module.config.HawkConfigManager;

/**
 * Defines the various modes which helps hawk script developer
 * @version 1.0 9 Apr, 2010
 * @author msahu
 * @see BuildModeEnum
 */
public class ScriptUsage {

    /**
     * defines the build mode
     */
    private  BuildModeEnum  buildMode;

    /**
     * path of the hawk script
     */
    private   String hawkScript;


    /**
     * SingleTon instance of <tt>ScriptUsage</tt>
     */
    private static ScriptUsage instance = new ScriptUsage();

    /**
     * Private CTOR to facilitate singletonness of this class
     */
    private ScriptUsage(){
        this.buildMode = BuildModeEnum.SCRIPTING;
    }

    /**
     * returns the singleton instance
     * @return the singleton instance
     */
    public static ScriptUsage getInstance(){
        return instance;
    }

    /**
     * Returns hawkScript file path
     * @return hawkScript file path
     */
    public String getHawkScript() {
        return hawkScript;
    }

    /**
     * Setter hawkScript file path
     * @param hawkScript sets the hawk script file path
     */
    public void setHawkScript(String hawkScript) {
        this.hawkScript = hawkScript;
    }

    

    /**
     * shows the various mode of running the run-perf-hawk.sh
     * 
     */
    public  void helpUser(){
        String prefix = "                     -";
        System.out.println(prefix+BuildModeEnum.DEBUG.toString().toLowerCase() +" -f {hawk file path}");
        System.out.println(prefix+BuildModeEnum.TRAINING.toString().toLowerCase());
        System.out.println(prefix+BuildModeEnum.PERF.toString().toLowerCase()  +" -f {hawk file path}");
        System.out.println(prefix+BuildModeEnum.COMPILE.toString().toLowerCase() +" -f {hawk file path}");
        System.out.println(prefix+BuildModeEnum.SCRIPTING.toString().toLowerCase() +" -f {hawk file path}");
        System.out.println(prefix+BuildModeEnum.VERSION.toString().toLowerCase());
    }

    /**
     * getter for <tt>BuildModeEnum</tt>
     * @return getter for <tt>BuildModeEnum</tt>
     */
    public  BuildModeEnum getBuildMode(){
        return buildMode;
    }

    /**
     * getter for <tt>BuildModeEnum</tt>
     * @param buildMode setter for <tt>BuildModeEnum</tt>
     */
    public void setBuildMode(BuildModeEnum buildMode){
        this.buildMode = buildMode;
    }

    /**
     * Parses the input arguments to find the mode <tt>BuildModeEnum</tt>
     * @param args array conataing user options
     * @return <tt>true</tt> on success <tt>false</tt> on failure
     * @throws org.hawk.exception.HawkException
     */
    public  boolean parseArguments(String args[]) throws HawkException{

        boolean isValid = false;
        try{
            if(args.length == 1){
                this.buildMode = BuildModeEnum.valueOf(args[0].substring(1).toUpperCase());
                
                switch(this.buildMode){
                    case HELP:
                    case TRAINING:
                    case VERSION:
                        isValid = true;
                        break;
                    default:
                        isValid = false;
                        break;
                }

            }else if(args.length ==0){
                this.buildMode = BuildModeEnum.HELP;
            }else if(args.length ==3 && args[1].equals("-f")){

                this.buildMode = BuildModeEnum.valueOf(args[0].substring(1).toUpperCase());
                
                switch(this.buildMode){
                    case DEBUG:
                    case COMPILE:
                    case PERF:
                    case SCRIPTING:

                        File hawkFile = new File(args[2]);
                        if(hawkFile.exists()){
                            this.hawkScript = args[2];
                            
                        }else{

                            throw new HawkException("Could not find hawk script.");
                        }
                        isValid = true;
                        break;
                    default:
                        isValid = false;
                        break;
                }

                

            }else{
                     return false;
            }
        }catch(Throwable th){
            throw new HawkException((th instanceof HawkException)?th.getMessage():"Invalid argument");
        }
       
        
        return isValid;
    }

    /**
     * displays all the tasks  implemented by  the user on the console.
     */
    public  void showTasks(){
        HawkConfigManager.getInstance().cacheConfig();
        String space = "                     ";
        String space1= "       ";
        Map<String,IModule> moduleMap = ModuleFactory.getInstance().getModules();
        for(Entry<String,IModule> entry: moduleMap.entrySet()){


            System.out.println(space+entry.getKey());
            System.out.println(space+space1+"|");
            System.out.println(space+space1+"|");
            Map<String,SubTaskContainer> map = entry.getValue().getSubTasks();

            for(Entry<String,SubTaskContainer> entry1:map.entrySet()){

                System.out.println(space+space1+"|------->"+entry1.getValue().getTaskSignature());
            }

            System.out.println();
            System.out.println();



        }

        
    }
}




