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
package org.hawk.executor.command.plot;

import java.util.ArrayList;
import java.util.List;
import org.hawk.codegen.FieldData;
import org.hawk.codegen.FileTemplateJavaBean;

import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkResponseHTML extends FileTemplateJavaBean {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkResponseHTML.class.getName());
    private static final String HAWKRESPONSEHTMLTEMPLATE = "conf/hawkresponse.html";
    @FieldData("#TARGET_MODULE#")
    private String targetModule;
    @FieldData(value = "#MODULE_NAME#", multiple = true, pre = false, post = true)
    private List<String> moduleNames = new ArrayList<String>();
    @FieldData(value = "#MODULE_SUBTASK_NAME#", multiple = true, pre = false, post = true)
    private List<String> moduleSubTaskNames = new ArrayList<String>();
    @FieldData("#MODULENAMEPATH#")
    private String moduleNamePath;
    @FieldData("#FILEPATH#")
    private String filePath;
    @FieldData("#START_TIMESTAMP#")
    private String startTimestamp;
    @FieldData("#END_TIMESTAMP#")
    private String endTimestamp;
    @FieldData("#DURATION_HOURS#")
    private String durationHours;
    @FieldData("#DURATION_MINS#")
    private String durationMins;
    @FieldData("#DURATION_SECS#")
    private String durationSecs;
    @FieldData("#MODULE_COUNT#")
    private String moduleCount;
    @FieldData("#MODULE_SUBTASK_COUNT#")
    private String moduleSubTaskCount;

    public String getTargetModule() {
        return targetModule;
    }

    public void setTargetModule(String targetModule) {
        this.targetModule = targetModule;
    }

    public List<String> getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(List<String> moduleNames) {
        this.moduleNames = moduleNames;
    }

    public boolean addModuleName(String moduleName) {
        return this.getModuleNames().add(moduleName);
    }

    public List<String> getModuleSubTaskNames() {
        return moduleSubTaskNames;
    }

    public void setModuleSubTaskNames(List<String> moduleSubTaskNames) {
        this.moduleSubTaskNames = moduleSubTaskNames;
    }

    public boolean addModuleSubTaskName(String moduleName) {
        return this.getModuleSubTaskNames().add(moduleName);
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

    public String getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(String durationHours) {
        this.durationHours = durationHours;
    }

    public String getDurationMins() {
        return durationMins;
    }

    public void setDurationMins(String durationMins) {
        this.durationMins = durationMins;
    }

    public String getDurationSecs() {
        return durationSecs;
    }

    public void setDurationSecs(String durationSecs) {
        this.durationSecs = durationSecs;
    }

    public String getModuleCount() {
        return moduleCount;
    }

    public void setModuleCount(String moduleCount) {
        this.moduleCount = moduleCount;
    }

    public String getModuleSubTaskCount() {
        return moduleSubTaskCount;
    }

    public void setModuleSubTaskCount(String moduleSubTaskCount) {
        this.moduleSubTaskCount = moduleSubTaskCount;
    }
    /*
     @Override
     public String getInputFile() {
     return HAWKRESPONSEHTMLTEMPLATE;
     }

     public static void main(String args[]) throws Exception {
    
     HawkResponseHTML hawkResponseHTML = new HawkResponseHTML();
     hawkResponseHTML.setFilePath("abc");
     //hawkResponseHTML.setModuleName("def");
     List<String> modules = new ArrayList<String>();
     modules.add("abc1");
     modules.add("abc2");
     modules.add("abc3");
     modules.add("abc4");

     hawkResponseHTML.setModuleNames(modules);
     List<String> subTasks = new ArrayList<String>();
     subTasks.add("abc1");
     subTasks.add("abc2");
     subTasks.add("abc3");
     subTasks.add("abc4");
     hawkResponseHTML.setModuleSubTaskNames(subTasks);
     hawkResponseHTML.setModuleNamePath("deff");
     hawkResponseHTML.setStartTimestamp("star");
     hawkResponseHTML.setEndTimestamp("end");
     hawkResponseHTML.setTargetModule("AGAIN TESTING YAAR");
     hawkResponseHTML.setModuleCount("4");
     hawkResponseHTML.setModuleSubTaskCount("4");
     hawkResponseHTML.setDurationHours("11");
     hawkResponseHTML.setDurationMins("12");
     hawkResponseHTML.setDurationSecs("13");
     hawkResponseHTML.setOutputFile("hawkResponse.html");
     hawkResponseHTML.setErrorFile("hawkResponseError.html");
     //    Map<Integer, String> data = FileUtil.dumpStringToMap(FileUtil.preProcess(hawkResponseHTML));

     HawkConfigHelper.configure();
     ITemplateService templateService = AppContainer.getInstance().getBean(TemplateServiceImpl.class);
     templateService.toFile(hawkResponseHTML);
     System.out.println("-----------");
     System.out.println(hawkResponseHTML.getCurrentData());
       


     }
     */
}
