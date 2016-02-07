


package org.hawk.data.perf.plotter;


import org.hawk.util.HawkImageUtil;
import org.hawk.logger.HawkLogger;
import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.exception.HawkException;
import org.hawk.module.config.HawkConfigManager;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.startup.HawkStartup;
import org.hawk.util.HawkUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import org.hawk.data.perf.HawkPerfData;
import org.hawk.data.perf.HawkPerfUtil;
import org.hawk.util.PropertiesUtil;
import static org.hawk.constant.HawkConstant.*;


/**
 * This plots the graph of modoules against their's response time.
 * Note: This requires <tt>gnuplot</tt> is installed on the system where hawk is runnnig.
 * @VERSION 1.0 14 Apr, 2010
 * @author msahu
 */
public class HawkPlotter {

    private static final  HawkLogger logger = HawkLogger.getLogger(HawkPlotter.class.getName());

    private static final String IMAGEATTR = " <img src=\"#MODULENAME#.png\" alt=\"\"/>";
    
    public static void main(String args[]){
        String logPath = System.getProperty("LOGPATH");
        if(logPath == null || logPath.isEmpty()){
            logger.warn("LOGPATH is not set... Current directory will be used for plotting");
            System.out.println("LOGPATH is not set... Current directory will be used for plotting ...");
            System.setProperty("LOGPATH", ".");
        }
        logger.info("LOGPATH={"+logPath+"}");
        System.out.println("LOGPATH={"+logPath+"}");
        ScriptUsage.getInstance().setBuildMode(BuildModeEnum.PLOTTING);
        HawkStartup.getInstance().startUp();
        HawkPerfDataCollector.processPerfData();
        
    }

    /**
     * This plots the graph of modoules against their's response time.
     * Note: This requires <tt>gnuplot</tt> is installed on the system where hawk is runnnig.
     * @return <tt> true</tt> on success , otherwise <tt>false</tt>
     */
    public static boolean plot(Map<String,Set<String>> modules) throws Exception{

        String perfDataFile = HawkPerfDataCollector.getPerfDataFilePath();
        logger.info("perf data file {"+perfDataFile+"}");

        String hawkConfPath = System.getProperty(HAWKCONF);
        Properties props = PropertiesUtil.loadProperties(hawkConfPath);
        String targetApp = HawkConfigManager.getInstance().getTargetApp(props);
        if (targetApp == null){
            targetApp = "dummy";
        }
        


        String responseHTML = null;
        try {
            responseHTML = HawkUtil.readFile("conf/hawkresponse.html",true);
        } catch (HawkException ex) {
            System.out.println("Could not read hawkresponse.html ..."+ex.getMessage());
            logger.severe("Could not read hawkresponse.html ..."+ex.getMessage());
            return false;
        }

        List<HawkPerfData> allboundary = HawkPerfUtil.fetchBoundaryPerfData(HawkPerfDataCollector.all);

        if(allboundary== null || allboundary.isEmpty()){
            logger.warn("No Data found to plot");
            return false;
        }

        long hawkStartTime = allboundary.get(0).getModuleStartTimeLong();
        long hawkEndTime = allboundary.get(1).getModuleStartTimeLong();
        long diff = hawkEndTime - hawkStartTime;
        diff = diff/1000;// in seconds
        double hour = diff/(60*60);
        double min = diff%(60*60);
        min = min/60;

        double secs = min%60;

        BigDecimal hourRoundedOff = (new BigDecimal(hour)).setScale(2,RoundingMode.HALF_UP);
        BigDecimal minRoundedOff = (new BigDecimal(min)).setScale(2,RoundingMode.HALF_UP);
        BigDecimal secsRoundedOff = (new BigDecimal(secs)).setScale(2,RoundingMode.HALF_UP);
        ReplacementData replacementData = new ReplacementData();
        replacementData.setTargetModule(targetApp);
        replacementData.setDuration_hours(hourRoundedOff.toString());
        replacementData.setDuration_mins(minRoundedOff.toString());
        replacementData.setDuration_secs(secsRoundedOff.toString());
        replacementData.setEndTimestamp(FORMATTER.format(new Date(hawkEndTime)));
        replacementData.setStartTimestamp(FORMATTER.format(new Date(hawkStartTime)));
        replacementData.setModuleName(targetApp);
        String logPath = System.getProperty("LOGPATH");
        logger.info("LOGPATH={"+logPath+"}");
        replacementData.setModuleNamePath(logPath+"./"+targetApp);

        responseHTML = replaceAll(responseHTML, replacementData);

        logger.info("Plotting {"+targetApp+"} using data file {"+perfDataFile+"}");
        plotInternal(targetApp, perfDataFile,replacementData.getStartTimestamp(),replacementData.getEndTimestamp());
        
        
        for(String module: modules.keySet()){
            if(HawkPerfDataCollector.perfDataModuleFilePathExists(module)){
                String modulePerfFilePath = HawkPerfDataCollector.getPerfDataModuleFilePath(module);
                List<HawkPerfData> all =  HawkPerfDataCollector.moduleMap.get(module);
                String startTime = null;
                String endTime = null;
                List<String> boundary = HawkPerfUtil.fetchBoundaryTime(all);
                startTime = boundary.get(0);
                endTime = boundary.get(1);
                plotInternal(module, modulePerfFilePath,startTime,endTime);
                responseHTML = responseHTML + IMAGEATTR;
                replacementData.setModuleName(module);
                responseHTML = replaceAll(responseHTML, replacementData);
            }
        }


        for(Entry<String,Set<String>> entry :modules.entrySet()){

            for(String subTask:entry.getValue()){

                String moduleSubTask = subTask;

                String modulePerfFilePath = HawkPerfDataCollector.getPerfDataModuleFilePath(moduleSubTask);
                List<HawkPerfData> all =  HawkPerfDataCollector.moduleTaskMap.get(moduleSubTask);
                String startTime = null;
                String endTime = null;
                List<String> boundary = HawkPerfUtil.fetchBoundaryTime(all);
                startTime = boundary.get(0);
                endTime = boundary.get(1);
                plotInternal(moduleSubTask, modulePerfFilePath,startTime,endTime);
                if(!responseHTML.contains(moduleSubTask)){
                    responseHTML = responseHTML + IMAGEATTR;
                    replacementData.setModuleName(moduleSubTask);
                    responseHTML = replaceAll(responseHTML, replacementData);
                }
                
            }

        }

        HawkImageUtil.copyLogo();
        System.out.println("path of index.html ===="+logPath+"/index.html");
        logger.info("path of index.html ===="+logPath+"/index.html");

        try {
            
            HawkUtil.writeFile(logPath+"/index.html", responseHTML);
        } catch (HawkException ex) {
            logger.severe("Could not write to index.html",ex);
            return false;
        }
        

        return true;
    }

    private static boolean plotInternal(String targetApp,String perfDataFile,String startTime,String endTime){
        String gnuTemplate = null;
        try {
            gnuTemplate = HawkUtil.readFile("conf/gnu.template",true);
            
        } catch (HawkException ex) {
            return false;
        }
        String logPath = System.getProperty("LOGPATH");
        ReplacementData replacementData = new ReplacementData();
        replacementData.setFilePath(perfDataFile);
        replacementData.setModuleName(targetApp);
        replacementData.setModuleNamePath(logPath+"/"+targetApp);
        replacementData.setStartTimestamp(startTime);
        replacementData.setEndTimestamp(endTime);

        
        
        String rtn = replaceAll(gnuTemplate, replacementData);

        
        String gnuDataFile = logPath+"/"+targetApp+".data";
        try {
            
            HawkUtil.writeFile(gnuDataFile, rtn);
        } catch (HawkException ex) {
            System.out.println("error while writing {"+gnuDataFile+"}"+ex.getMessage());
            logger.severe("error while writing {"+gnuDataFile+"}"+ex.getMessage());
            return false;
        }
        logger.info("Plotting {"+gnuDataFile+"}");
        createGNUPlotProcess(gnuDataFile);
        return true;
    }

    public static boolean createGNUPlotProcess(String gnuDataFile){

       return HawkUtil.executeProcess("gnuplot "+gnuDataFile);

   }
   


    private static class ReplacementData{
        String moduleName;
        String moduleNamePath;//for individual png
        String filePath;
        String targetModule;
        String startTimestamp;
        String endTimestamp;
        String duration_hours;
        String duration_mins;
        String duration_secs;

        public String getModuleNamePath() {
            return moduleNamePath;
        }

        public void setModuleNamePath(String moduleNamePath) {
            this.moduleNamePath = moduleNamePath;
        }

        

        public String getDuration_hours() {
            return duration_hours;
        }

        public void setDuration_hours(String duration_hours) {
            this.duration_hours = duration_hours;
        }

        public String getDuration_mins() {
            return duration_mins;
        }

        public void setDuration_mins(String duration_mins) {
            this.duration_mins = duration_mins;
        }

        public String getDuration_secs() {
            return duration_secs;
        }

        public void setDuration_secs(String duration_secs) {
            this.duration_secs = duration_secs;
        }

        public String getEndTimestamp() {
            return endTimestamp;
        }

        public void setEndTimestamp(String endTimestamp) {
            this.endTimestamp = endTimestamp;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public String getStartTimestamp() {
            return startTimestamp;
        }

        public void setStartTimestamp(String startTimestamp) {
            this.startTimestamp = startTimestamp;
        }

        public String getTargetModule() {
            return targetModule;
        }

        public void setTargetModule(String targetModule) {
            this.targetModule = targetModule;
        }


        }

    private static String replaceAll(String template,ReplacementData replacementData){

        String rtn = template.replaceAll("#MODULENAME#", replacementData.getModuleName());
        rtn = rtn.replaceAll("#FILEPATH#", replacementData.getFilePath());
        rtn = rtn.replaceAll("#MODULENAMEPATH#", replacementData.getModuleNamePath());
        rtn = rtn.replaceAll("#TARGET_MODULE#", replacementData.getTargetModule());
        rtn = rtn.replaceAll("#START_TIMESTAMP#", replacementData.getStartTimestamp());
        rtn = rtn.replaceAll("#END_TIMESTAMP#", replacementData.getEndTimestamp());
        rtn = rtn.replaceAll("#DURATION_HOURS#", replacementData.getDuration_hours());

        rtn = rtn.replaceAll("#DURATION_MINS#", replacementData.getDuration_mins());
        rtn = rtn.replaceAll("#DURATION_SECS#", replacementData.getDuration_mins());
        rtn = rtn.replaceAll("#STARTTIME#", replacementData.getStartTimestamp());
        rtn = rtn.replaceAll("#ENDTIME#", replacementData.getEndTimestamp());

        
        return rtn;
    }
}




