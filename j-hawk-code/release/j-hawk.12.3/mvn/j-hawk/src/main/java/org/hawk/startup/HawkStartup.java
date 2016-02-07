


package org.hawk.startup;

import org.hawk.module.ModuleFactory;
import org.apache.log4j.PropertyConfigurator;
import java.util.logging.Logger;
import org.hawk.module.config.HawkConfigManager;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;
import java.lang.reflect.Method;
import org.hawk.logger.HawkLogger;
import static org.hawk.constant.HawkConstant.*;

/**
 * This is a startup class.
 * <p> Apart from initializing Hawk, it kicks off the startup of<br>
 * target application.<br>
 * Note: The target module should implement <tt>IModule</tt>.
 * @VERSION 1.0 9 Apr, 2010
 * @author msahu
 */
public class HawkStartup {

    private static final Logger rootLogger = Logger.getLogger("");

    private static final HawkLogger logger = HawkLogger.getLogger(HawkStartup.class.getName());

    private static HawkStartup instance = new HawkStartup();


    public boolean configureLogging(){

        boolean loggerConfigured = false;

        String log4jPropsPath = System.getProperty(LOG4JPROPSKEY);

        if(log4jPropsPath != null){

            PropertyConfigurator.configure(log4jPropsPath);

            loggerConfigured = true;

        }else{

            loggerConfigured = false;

        }

        return loggerConfigured;

    }


    
    private HawkStartup(){

    }

    /**
     * SingleTon accessor method
     * @return
     */
    public static HawkStartup getInstance(){
        return instance;
    }
    

    /**
     * This starts the targe app.
     * @return
     */
    public boolean startUp(){

        BuildModeEnum buildMode = ScriptUsage.getInstance().getBuildMode();

        if(buildMode == null || buildMode == BuildModeEnum.SCRIPTING || buildMode == BuildModeEnum.VERSION){

            ModuleFactory.getInstance().cacheLibraryModules();
            return true;

        }

        if(buildMode == null || buildMode != BuildModeEnum.PLOTTING){

            HawkConfigManager.getInstance().cacheConfig();

        }

        boolean status = true;

        if(buildMode == null || buildMode == BuildModeEnum.PERF){
        
            status  = startupTargetModule();

        }

        status = configureLogging();
        return status;
    }

    private boolean startupTargetModule(){

        
        String targetMainModule = HawkConfigManager.getInstance().getTargetMainModule();

        String targetPackage = HawkConfigManager.getInstance().getTargetModulePackage();

        String targetModuleClazzStr = targetPackage + SEPARATOR + targetMainModule;

        Class targetModuleClazz = null;

        try{

            targetModuleClazz = Class.forName(targetModuleClazzStr);

            Object targetModuleInstance = targetModuleClazz.newInstance();

            Class noArgs[] = null;

            Method startUpMethod = targetModuleClazz.getMethod("startUp", noArgs);

            startUpMethod.invoke(targetModuleInstance, new Object[]{});

        }catch(Throwable th){

            logger.severe("Failed to invoke startup method of target app");

        }
        return true;
        
    }
}




