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
package org.hawk.codegen;

import org.commons.file.FileUtil;


import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
public class FileTemplateJavaBean {

    private static final HawkLogger logger = HawkLogger.getLogger(FileTemplateJavaBean.class.getName());
    private String currentData;
    private String outputFile;
    private String errorFile;
    private String inputFile;

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getErrorFile() {
        return errorFile;
    }

    public void setErrorFile(String errorFile) {
        this.errorFile = errorFile;
    }

    public void setCurrentData(String currentData) {
        this.currentData = currentData;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String fileTemplate) {
        this.inputFile = fileTemplate;
    }

    public boolean loadCurrentData() throws Exception {

        if (this.validate()) {
            this.setCurrentData(FileUtil.readFile(this.getInputFile()));
        }

        return true;
    }

    public String getCurrentData() {
        return currentData;
    }

    public boolean validate()  {
        boolean exists = FileUtil.exists(this.getInputFile());
        return exists;
    }

}
