/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
package org.hawk.template;

import org.commons.file.FileUtil;


import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
//@Component(FILETEMPLATEJAVABEAN)
//@Qualifier(DEFAULTQUALIFIER)
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
