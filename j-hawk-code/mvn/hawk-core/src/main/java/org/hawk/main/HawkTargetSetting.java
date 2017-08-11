/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.main;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hawk.config.IHawkConfig;
import org.hawk.config.clazzloader.ClassPathHacker;
import org.hawk.constant.HawkConstant;
import static org.hawk.constant.HawkConstant.HAWKJAVACLASSPATH;
import org.common.di.AppContainer;

import org.hawk.logger.HawkLogger;
import org.hawk.module.properties.HawkModulePropertiesManager;
import org.common.properties.PropertiesUtil;
import org.commons.reflection.ClazzUtil;


/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class HawkTargetSetting {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkTargetSetting.class.getName());
    private Properties targetAppSettingProps = null;
    private File settingFile = null;

    public Properties getTargetAppSettingProps() {
        return targetAppSettingProps;
    }

    public void setTargetAppSettingProps(Properties targetAppSettingProps) {
        this.targetAppSettingProps = targetAppSettingProps;
    }

    public File getSettingFile() {
        return settingFile;
    }

    public void setSettingFile(File settingFile) {
        this.settingFile = settingFile;
        this.targetAppSettingProps = PropertiesUtil.loadProperties(settingFile);
    }

    public void reset() {
        targetAppSettingProps = null;
        settingFile = null;
    }

    public String getProperty(String key) {
        String rtn = System.getProperty(key);
        if (rtn == null || rtn.trim().isEmpty()) {
            Properties props = this.getTargetAppSettingProps();
            if (props != null) {
                StringBuilder settingKey = new StringBuilder(HAWK_JAVA_PROP);
                settingKey.append(HawkConstant.SEPARATOR);
                settingKey.append(key);
                rtn = props.getProperty(settingKey.toString());
            }
        }

        return rtn;
    }

    public String findTargetSetting() {
        String setttingsConf = System.getProperty("setting");
        if (setttingsConf == null || setttingsConf.trim().isEmpty()) {
            File tmpSettingFile = new File("setting.conf");
            if (tmpSettingFile.exists()) {
                setttingsConf = "setting.conf";
            }
        }
        File file = null;
        if(setttingsConf != null && !setttingsConf.trim().isEmpty()){
            file = new File(setttingsConf);
        }
        if( file != null && file.exists()){
            this.setSettingFile(file);
        }else{
            logger.error("could not find settting file {"+setttingsConf+"}");
            setttingsConf = null;
        }
        return setttingsConf;
    }

    public boolean configure() throws Exception {

        return this.configureInternal();

    }

    private boolean configureInternal() throws Exception {
        boolean status = true;
        this.findTargetSetting();
//        ModuleCache moduleCache = AppContainer.getInstance().getBean(MODULECACHE, ModuleCache.class);
  //      HawkModulePropertiesManager hawkConfigManager = AppContainer.getInstance().getBean(HAWKMODULEPROPERTIESMANAGER, HawkModulePropertiesManager.class);



        if (this.getSettingFile() != null && this.getSettingFile().exists()) {
            try {
                ClassPathHacker.addFiles(this.getTargetAppSettingProps().getProperty(HAWKJAVACLASSPATH));
               // hawkConfigManager.cacheConfig();
                this.configureTargetApp();
            } catch (Throwable th) {
                logger.error("error while integrating target jar with j-hawk framework", th);

                status = false;

            }
        } else {
         //   moduleCache.cacheCoreModules();
          //  moduleCache.cacheSubTasks();
        }



        return status;
    }

    private boolean configureTargetApp() throws Exception {


        String targetModuleClazzStr = AppContainer.getInstance().getBean( HawkModulePropertiesManager.class).getTargetMainModule();

        boolean result;

        IHawkConfig targetModuleHawkConfig = null;
        try {
            targetModuleHawkConfig = ClazzUtil.instantiate(targetModuleClazzStr, IHawkConfig.class);
        } catch (Exception ex) {
            Logger.getLogger(HawkTargetSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (targetModuleHawkConfig != null) {
            result = targetModuleHawkConfig.configure();
        } else {
            result = false;
        }
        return result;

    }

   
    private static final String HAWK_JAVA_PROP = "hawk.java.prop";
}
