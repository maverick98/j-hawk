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
package org.hawk.input;

import java.io.File;
import java.util.Map;
import org.commons.file.FileUtil;

import org.hawk.logger.HawkLogger;

/**
 *
 * @author manosahu
 */
   
   
public class HawkInput {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkInput.class.getName());
    /**
     * This stores the entire hawk script file's contents in a map with key
     * value as line no and line respectively.
     */
    private Map<Integer, String> scriptMap = null;

    public Map<Integer, String> getScriptMap() {
        return scriptMap;
    }

    public void setScriptMap(Map<Integer, String> scriptMap) {
        this.scriptMap = scriptMap;
    }

   
    
   

    public void reset() {
        this.setScriptMap(null);
    }

    /**
     * This reads the hawk script file and returns them as a map with line no
     * and script as key value pair respectively.
     *
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    public Map<Integer, String> readScript(File scriptFile) {

        this.scriptMap = FileUtil.dumpFileToMap(scriptFile);

        return this.scriptMap;
    }

    /**
     * This reads the hawk script file and returns them as a map with line no
     * and script as key value pair respectively.
     *
     * @param scriptFile hawk script file
     * @return a map containing line no and the script
     */
    public Map<Integer, String> readScript(String scriptFile) {

        this.scriptMap = FileUtil.dumpStringToMap(scriptFile);

        return this.scriptMap;
    }
}
