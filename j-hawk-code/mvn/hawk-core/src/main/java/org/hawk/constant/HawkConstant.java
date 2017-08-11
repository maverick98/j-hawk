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

package org.hawk.constant;

/**
 * This is the place holder for constants.
 * @author msahu
 */
public interface HawkConstant {

    /**
     * This contains the hawk's release VERSION
     */
    String VERSION="14.11";

    String J_HAWK_IDE_TITLE = "j-hawk IDE";

    /**
     * svn revn no of the repository
     */
    int SVN_REVN_NO = 411;
    /**
     * This contains the name of main thread in java
     */
    String MAINTHREADNAME ="main";

    String FORMATTER = "MM/dd/yy hh:mm:ss";

    
    /**
     * This contains HawkConfig objects
     */
    String HAWK_CONFIG_PACKAGE = "org.hawk.module.config";

    
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

    String ACTION_NAME="actionName";

    String OUT="out";

    String HTTP_RESPONSE_CODE = "httpRespCode";

    String HTTP_RESPONSE_MESSAGE = "httpRespMsg";

    String CONCURRENT="concurrent";
    
    String USE_REST="rest";

    String TARGET_URL="targetURL";

    String UPLOAD_FILE="uploadFile";

    String MULTIPART_POST="multiPartPOST";

    String INVALIDATE_SESSION="invalidateSession";
    
    String SHOULD_DUMP = "dump";

    String FILE_NAME="fileName";

    String HTTP_HEADER="httpHeader";

    String HTTP_BODY="httpBody";

    String HTTP_REQUEST="httpRequest";
    
    String HTTP_AUTH="auth";
    
    

    String HTTP_METHOD="method";

    String HTTP_GET="GET";

    String HTTP_POST="POST";

    String LOGPATH="LOGPATH";
    
    String ENCODING = "UTF-8";
    
    String HAWKJAVACLASSPATH = "hawk.java.classpath";
    
   
    
    
    
}
