
package org.hawk.constant;

import java.text.SimpleDateFormat;

/**
 * This is the place holder for constants.
 * @author msahu
 */
public interface HawkConstant {

    /**
     * This contains the hawk's release VERSION
     */
    String VERSION="12.3";

    /**
     * svn revn no of the repository
     */
    int SVN_REVN_NO = 352;
    /**
     * This contains the name of main thread in java
     */
    String MAINTHREADNAME ="main";

    SimpleDateFormat FORMATTER = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
    /**
     * This contains all the scripts implemented by Hawk.
     */
    String SCRIPT_PACKAGE = "org.hawk.module.script";

    /**
     * This contains HawkConfig objects
     */
    String HAWK_CONFIG_PACKAGE = "org.hawk.module.config";

    /**
     * This is the package where hawk's module libraries reside
     */
     String HAWK_MODULE_LIBRARY_PACKAGE="org.hawk.module.lib";
    /**
     * Hawk's name
     */
    String HAWK="hawk";

    /**
     * Separator used in hawk
     */
    String SEPARATOR=".";

    /**
     * The -D option to be used while running hawk
     */
    String HAWKCONF="hawk.conf";

    /**
     * Hawk iteration key
     */
    String HAWK_ITERATION="hawk.iteration";

    /**
     * Hawk iteration think time in seconds
     */
    String HAWK_ITERATION_THINKTIME="hawk.iteration.thinktime";

    /**
     * main module
     */
    String MAIN_MODULE="mainModule";

    /**
     * hawk main module
     */
    String HAWK_MAIN_MODULE = HAWK+SEPARATOR+MAIN_MODULE;

    

    /**
     * Duration hawk execution in seconds.
     */
    String HAWK_DURATION="hawk.duration";

    int MINUTE_SECS = 60;


    /**
     * hawk perf data seprartor .
     * used by gnuplot
     */
    String HAWK_PERF_DATA_SEPRATOR=" ";


    /**
     * Target application config package key
     */
    String TARGET_MODULE_CONFIG_PACKAGE_KEY = "hawk.target.application.module.config.package";

    /**
     * Target application key
     */
    String TARGET_APP_KEY = "hawk.target.application";

    /**
     * Target application main module
     */
    String HAWK_TARGET_MAIN_MODULE = "hawk.IModule.main.module";


    /**
     * Target application module package key
     */
    String TARGET_MODULE_PACKAGE_KEY = "hawk.target.application.module.package";


    /**
     * Target application module implementor key
     */
    String TARGET_MODULE_IMPLEMENTOR_KEY = "hawk.IModule.implementor";


    /**
     * This is used in the http request. The sample xml file is annotated with
     * hawkParam to indicate that it is a part of http request to be constructed
     */
    String HAWKPARAM ="hawkParam";

    /**
     * This is used in the http request. The sample xml file is annotated with hawkStruct
     * indicating that a new hawk structure has to be created
     *
     */
    String HAWKSTRUCT ="hawkStruct";

    String HAWK_EXECUTION_PARAMS="params";

    String HAWK_EXECUTION_PARAMS_DELIMETER=",";


    String LOG4JPROPSKEY = "hawkLogger";
    
}
