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
