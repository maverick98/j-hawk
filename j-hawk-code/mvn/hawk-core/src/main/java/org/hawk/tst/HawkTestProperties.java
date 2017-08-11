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
package org.hawk.tst;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import static org.hawk.constant.HawkConstant.HAWK;
import static org.hawk.constant.HawkConstant.SEPARATOR;
import org.common.properties.PropertiesUtil;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class HawkTestProperties {

    private String testConfig = "conf/hawktst.conf";
    
    private Properties props = null;
    
    private Map<String,String> testSuites = new LinkedHashMap<>();

    public Map<String, String> getTestSuites() {
        return testSuites;
    }

    public void setTestSuites(Map<String, String> testSuites) {
        this.testSuites = testSuites;
    }

    
    public String getTestConfig() {
        return testConfig;
    }

    public void setTestConfig(String testConfig) {
        this.testConfig = testConfig;
    }
    
    
    public boolean isThereAnySuite(){
        return !this.getTestSuites().isEmpty();
    }
    public boolean reset(){
        boolean status;
        if(this.isThereAnySuite()){
            this.testSuites.clear();
            status = true;
        }else{
            status= false;
        }
        return status ;
    }
    public boolean put(String testSuite, String testScript){
        boolean status = false;
        this.getTestSuites().put(testSuite, testScript);
        return status;
    }
    public boolean isLoaded(){
        return this.isThereAnySuite();
    }

    public boolean load() {
        this.reset();
        this.props = PropertiesUtil.loadConfigsFromClazzpath(this.getTestConfig());
        String path = this.props.getProperty("regression_script_path");
        String tsts = this.props.getProperty("regression_scripts");
        StringTokenizer strTok = new StringTokenizer(tsts, ",");
        String token;
        
        while (strTok.hasMoreTokens()) {
            token = strTok.nextToken();
            
            StringBuilder sb = new StringBuilder(path);
            sb.append(File.separator);
            sb.append(token);

            if (!token.endsWith(HAWK)) {
                sb.append(SEPARATOR);
                sb.append(HAWK);
            }
            this.put(token,sb.toString());
        }
        return this.isThereAnySuite();

    }
}
