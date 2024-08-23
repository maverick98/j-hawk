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
package org.hawk.executor.command.plot;

import org.hawk.codegen.FieldData;
import org.hawk.codegen.FileTemplateJavaBean;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
public class PlottingData extends  FileTemplateJavaBean {

    private static final String GNUTEMPLATE = "conf/gnu.template";
    @FieldData("#MODULENAME#")
    private String moduleName;
    @FieldData("#MODULENAMEPATH#")
    private String moduleNamePath;
    @FieldData("#FILEPATH#")
    private String filePath;
    @FieldData("#STARTTIME#")
    private String startTimestamp;
    @FieldData("#ENDTIME#")
    private String endTimestamp;

    private String gnuDataFile;

    public String getGnuDataFile() {
        return gnuDataFile;
    }

    public void setGnuDataFile(String gnuDataFile) {
        this.gnuDataFile = gnuDataFile;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleNamePath() {
        return moduleNamePath;
    }

    public void setModuleNamePath(String moduleNamePath) {
        this.moduleNamePath = moduleNamePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(String endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String toString() {
        return "GNUTemplate{" + "moduleName=" + moduleName + ", moduleNamePath=" + moduleNamePath + ", filePath=" + filePath + ", startTimestamp=" + startTimestamp + ", endTimestamp=" + endTimestamp + '}';
    }

    //   @Override
    public String getInputFile() {
        return GNUTEMPLATE;
    }

    private static final HawkLogger logger = HawkLogger.getLogger(PlottingData.class.getName());
}
