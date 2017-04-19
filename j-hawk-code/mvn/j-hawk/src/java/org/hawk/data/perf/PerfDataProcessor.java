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
package org.hawk.data.perf;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.commons.file.FileUtil;
import org.commons.process.ProcessUtil;
import static org.hawk.constant.HawkConstant.FORMATTER;
import static org.hawk.constant.HawkConstant.HAWK_PERF_DATA_SEPRATOR;
import org.hawk.data.perf.HawkPerfData.PerfDataCmpEnum;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkCommandParser;
import org.hawk.executor.command.plot.HawkResponseHTML;
import org.hawk.executor.command.plot.PlottingData;
import org.hawk.executor.command.plot.PlottingExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;
import org.hawk.module.annotation.CollectPerfData;
import org.hawk.module.properties.HawkModulePropertiesManager;
import org.hawk.util.HawkImageUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This collects the perf data from all the modules execution.
 * Refactor me.. it is so fat... get back to that sexy figure
 * @VERSION 1.0 6 Apr, 2010
 * @author msahu
 */
//@Component(HAWKPERFDATACOLLECTOR)
//@Qualifier(DEFAULTQUALIFIER)
public class PerfDataProcessor {

    private final HawkLogger logger = HawkLogger.getLogger(PerfDataProcessor.class.getName());
    private final int DUMPSIZE = 500;
    private static final String PERF_DATA_FILE = "perf.data";
    private List<HawkPerfData> allPerfData = new ArrayList<HawkPerfData>();
    private static ThreadLocal<HawkPerfData> currentPerfDataLocal = new ThreadLocal<HawkPerfData>();
    @Autowired(required = true)
    //@Qualifier(DEFAULTQUALIFIER)
    private PlottingExecutor plottingExecutor;

    public PlottingExecutor getPlottingExecutor() {
        return plottingExecutor;
    }

    public void setPlottingExecutor(PlottingExecutor plottingExecutor) {
        this.plottingExecutor = plottingExecutor;
    }

    public List<HawkPerfData> getAllPerfData() {
        return allPerfData;
    }

    public void setAllPerfData(List<HawkPerfData> allPerfData) {
        this.allPerfData = allPerfData;
    }

    private boolean resetCurrentPerfData() {
        boolean status = false;
        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        if (currentPerfData != null) {
            currentPerfDataLocal.set(null);

            status = true;

        }

        return status;
    }

    private boolean resetCollector() {

        allPerfData = new ArrayList<HawkPerfData>();

        resetCurrentPerfData();

        return true;
    }

    private boolean createPerfData() {

        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        if (currentPerfData == null) {

            currentPerfData = new HawkPerfData();

            currentPerfDataLocal.set(currentPerfData);

            return true;

        }

        return false;
    }

    /**
     * This indicates whether the current module is started or not.
     *
     * @return
     */
    private boolean isStarted() {

        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        return currentPerfData != null;
    }

    /**
     * This indicates whether the current module is ended or not.
     *
     * @return
     */
    private boolean isEnded() {

        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        return currentPerfData == null;
    }

    private boolean shouldCollectPerfData(IModule module) {
        boolean shouldCollect;

        if (!AppContainer.getInstance().getBean(HawkCommandParser.class).shouldCollectPerfData()) {
            return false;
        }
        Class moduleClazz = module.getClass();
        if (moduleClazz.isAnnotationPresent(CollectPerfData.class)) {

            CollectPerfData collectPerfData = (CollectPerfData) moduleClazz.getAnnotation(CollectPerfData.class);
            shouldCollect = !collectPerfData.myself();
        } else {
            shouldCollect = true;
        }
        return shouldCollect;
    }

    /**
     * This starts perf data collection
     *
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully stared otherwise
     * <tt>false</tt>.
     */
    public boolean start(IModule module, String moduleSubTask) {
        boolean status = false;
        if (this.shouldCollectPerfData(module)) {
            String moduleName = module.getName();
            status = this.start(moduleName, moduleSubTask);;
        }
        return status;
    }

    /**
     * This starts perf data collection
     *
     * @parm module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully stared otherwise
     * <tt>false</tt>.
     */
    public boolean start(String moduleName, String moduleSubTask) {

        if (isEnded()) {

            this.createPerfData();

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            return currentPerfData.start(moduleName, moduleSubTask);

        }


        return false;
    }

    /**
     * This starts perf data collection Note: Here the module's default task
     * "NONE" is considered as subtask.
     *
     * @param module
     * @return returns <tt>true</tt> if it is successfully stared otherwise
     * <tt>false</tt>.
     */
    public boolean start(IModule module) {

        return this.start(module, "NONE");
    }

    /**
     * This starts perf data collection Note: Here the module's default task
     * "NONE" is considered as subtask.
     *
     * @param module
     * @return returns <tt>true</tt> if it is successfully stared otherwise
     * <tt>false</tt>.
     */
    public boolean start(String moduleName) {

        return this.start(moduleName, "NONE");
    }

    /**
     * This ends perf data collection
     *
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean end(IModule module, String moduleSubTask) {
        boolean result = false;
        if (this.shouldCollectPerfData(module)) {
            String moduleName = module.getName();
            result = this.end(moduleName, moduleSubTask);
        }
        return result;
    }

    /**
     * This ends perf data collection
     *
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean end(String moduleName, String moduleSubTask) {

        if (isStarted()) {

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            boolean status = currentPerfData.end(moduleName, moduleSubTask);

            allPerfData.add(currentPerfData);

            this.dump();

            this.resetCurrentPerfData();

            return status;

        }

        return false;
    }

    /**
     * This ends perf data collection Note: Here the module's default task
     * "NONE" is considered as subtask.
     *
     * @param module
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean end(IModule module) {

        return end(module.getName());
    }

    /**
     * This ends perf data collection Note: Here the module's default task
     * "NONE" is considered as subtask.
     *
     * @param module
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean end(String moduleName) {

        return this.end(moduleName, "NONE");
    }

    /**
     * This ends perf data collection for failure case
     *
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean endWithFailure(IModule module, String moduleSubTask) {

        boolean result = false;
        if (this.shouldCollectPerfData(module)) {
            String moduleName = module.getName();
            result = this.endWithFailure(moduleName, moduleSubTask);
        }
        return result;
    }

    /**
     * This ends perf data collection for failure case
     *
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean endWithFailure(String moduleName, String moduleSubTask) {

  //      return this.endWithFailure(moduleName,moduleSubTask );
        return false;
    }

    /**
     * This ends perf data collection for failure case
     *
     * @param module
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean endWithFailure(IModule module) {

    //    return endWithFailure(module.getName(),"NONE");
        return false;
    }

    /**
     * This ends perf data collection for failure case
     *
     * @param module
     * @return returns <tt>true</tt> if it is successfully ended otherwise
     * <tt>false</tt>.
     */
    public boolean endWithFailure(String moduleName) {

        return this.endWithFailure(moduleName, "NONE");
    }

    private boolean shouldDump() {
        return (allPerfData.size() > DUMPSIZE);
    }

    private String getPerfDataFilePath() {

        String logPath = System.getProperty("LOGPATH");

        String logFilePath = logPath + "/" + PERF_DATA_FILE;

        return logFilePath;
    }

    private String getPerfDataModuleFilePath(String module) {

        String logPath = System.getProperty("LOGPATH");

        String logFilePath = logPath + "/" + module + "-" + PERF_DATA_FILE;

        return logFilePath;
    }

    /*
    private boolean perfDataModuleFilePathExists(String module) {

        String filePath = getPerfDataModuleFilePath(module);

        File f = new File(filePath);

        return f.exists();
    }
    */ 

    private boolean perfDataExists() {

        String filePath = getPerfDataFilePath();

        File f = new File(filePath);

        return f.exists();
    }

    private boolean dump() {
        return this.dump(this.shouldDump());
    }

    public boolean dump(boolean shouldDump) {

        if (shouldDump) {

            String logFilePath = this.getPerfDataFilePath();

            if (this.writePerfData(allPerfData, logFilePath)) {

                this.resetCollector();

                return true;

            }

        }

        return false;

    }

    private boolean writePerfData(List<HawkPerfData> all, String logFilePath) {
        StringBuilder sb = new StringBuilder();
        for (HawkPerfData perfData : all) {

            sb.append(perfData.getData());

        }
        boolean status;
        
            status = FileUtil.writeFile(logFilePath, sb.toString(), true);
        
        return status;
    }

    private HawkPerfData parsePerfData(String line) {
        HawkPerfData perfData = null;
        Matcher matcher = PERF_DATA_PATTERN.matcher(line);

        if (matcher.matches()) {

            String module = matcher.group(1);

            String moduleSubTask = matcher.group(2);

            String moduleStartTime = matcher.group(3);

            String moduleEndTime = matcher.group(4);


            long moduleExecTime = Long.parseLong(matcher.group(5));

            String moduleExecStatus = matcher.group(6);


            perfData = new HawkPerfData();

            perfData.setModule(module);

            perfData.setModuleSubTask(moduleSubTask);

            perfData.setModuleStartTime(moduleStartTime);


            perfData.setModuleEndTime(moduleEndTime);

            perfData.setModuleExecutionTime(moduleExecTime);

            perfData.setModuleExecutionStatus(moduleExecStatus);


        }
        return perfData;
    }

    private List<HawkPerfData> readPerfData() throws Exception {

        if (!perfDataExists()) {
            throw new Exception("could not find perf.data file ....");
        }
        List<HawkPerfData> all = new ArrayList<HawkPerfData>();
        HawkPerfData perfData;
        Map<Integer, String> map = FileUtil.dumpFileToMap(this.getPerfDataFilePath());
        for (Entry<Integer, String> entry : map.entrySet()) {
            String line = entry.getValue();
            perfData = this.parsePerfData(line);
            if (perfData != null) {
                this.add(all, perfData);
            }
        }
        return all;
    }

    private boolean add(List<HawkPerfData> allData, HawkPerfData thatData) {
        boolean status = false;

        boolean shouldAdd = false;

        if (!allData.isEmpty()) {

            for (Iterator<HawkPerfData> itr = allData.iterator(); itr.hasNext();) {

                HawkPerfData thisData = itr.next();

                PerfDataCmpEnum cmpStatus = thisData.compare(thatData);

                if (cmpStatus != null && cmpStatus == PerfDataCmpEnum.SAME_TASK_LESS_START_TIME) {

                    itr.remove();

                    shouldAdd = true;

                } else if (cmpStatus != null) {

                    shouldAdd = true;

                }

            }

        } else {

            shouldAdd = true;

        }

        if (shouldAdd) {

            allData.add(thatData);

            status = true;

        }



        return status;
    }

    public void main(String args[]) {
        processPerfData();
    }

    private boolean writePerfData(Map<String, List<HawkPerfData>> map) {
        for (Entry<String, List<HawkPerfData>> entry : map.entrySet()) {

            String modulePerfFilePath = this.getPerfDataModuleFilePath(entry.getKey());

            writePerfData(entry.getValue(), modulePerfFilePath);



        }
        return true;
    }

    public List<PlottingData> createPlotData(Map<String, List<HawkPerfData>> map) {
        List<PlottingData> result = new ArrayList<PlottingData>();
        for (Entry<String,List<HawkPerfData>> entry : map.entrySet()) {
            String key = entry.getKey();
            String modulePerfFilePath = this.getPerfDataModuleFilePath(key);
            List<HawkPerfData> all = entry.getValue();
            String startTime;
            String endTime;
            List<String> boundary = HawkPerfUtil.fetchBoundaryTime(all);
            startTime = boundary.get(0);
            endTime = boundary.get(1);

            String logPath = System.getProperty("LOGPATH");
            PlottingData plottingData = new PlottingData();
            plottingData.setFilePath(modulePerfFilePath);
            plottingData.setModuleName(key);
            plottingData.setModuleNamePath(logPath + "/" + key);
            plottingData.setStartTimestamp(startTime);
            plottingData.setEndTimestamp(endTime);


        }
        return result;
    }

    public List<PlottingData> createSubTaskPlotData(Map<String, List<HawkPerfData>> moduleMap) {
        List<PlottingData> result = new ArrayList<PlottingData>();

        return result;
    }

    public boolean processPerfData() {

        List<HawkPerfData> all;
        try {
            all = this.readPerfData();

        } catch (Exception ex) {

            logger.error(ex);

            return false;
        }
        Map<String, List<HawkPerfData>> moduleMap = HawkPerfData.groupByModule(all);
        this.writePerfData(moduleMap);
        Map<String, List<HawkPerfData>> subTaskMap = HawkPerfData.groupByModuleSubTask(all);
        this.writePerfData(subTaskMap);
        String targetApp = AppContainer.getInstance().getBean( HawkModulePropertiesManager.class).getTargetApp();
        if (targetApp == null) {
            targetApp = "dummy";
        }

        List<HawkPerfData> allboundary = HawkPerfUtil.fetchBoundaryPerfData(all);

        if (allboundary == null || allboundary.isEmpty()) {
            logger.warn("No Data found to plot");
            return false;
        }

        long hawkStartTime = allboundary.get(0).getModuleStartTimeLong();
        long hawkEndTime = allboundary.get(1).getModuleStartTimeLong();
        long diff = hawkEndTime - hawkStartTime;
        diff = diff / 1000;// in seconds
        double hour = diff / (double) (60 * 60);
        double min = diff % (60 * 60);
        min = min / (double) 60;

        double secs = min % 60;

        BigDecimal hourRoundedOff = (new BigDecimal(hour)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal minRoundedOff = (new BigDecimal(min)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal secsRoundedOff = (new BigDecimal(secs)).setScale(2, RoundingMode.HALF_UP);
        HawkResponseHTML hawkResponseHTML = new HawkResponseHTML();
        hawkResponseHTML.setTargetModule(targetApp);
        hawkResponseHTML.setDurationHours(hourRoundedOff.toString());
        hawkResponseHTML.setDurationMins(minRoundedOff.toString());
        hawkResponseHTML.setDurationSecs(secsRoundedOff.toString());
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATTER);
        hawkResponseHTML.setEndTimestamp(formatter.format(new Date(hawkEndTime)));
        hawkResponseHTML.setStartTimestamp(formatter.format(new Date(hawkStartTime)));

        String logPath = System.getProperty("LOGPATH");
        logger.info("LOGPATH={" + logPath + "}");
        hawkResponseHTML.setModuleNamePath(logPath + "./" + targetApp);

        
        PlottingData plottingData = new PlottingData();
        String modulePerfFilePath = this.getPerfDataModuleFilePath(targetApp);
        plottingData.setFilePath(modulePerfFilePath);
        plottingData.setModuleName(targetApp);
        plottingData.setModuleNamePath(logPath + "/" + targetApp);
        plottingData.setStartTimestamp(hawkResponseHTML.getStartTimestamp());
        plottingData.setEndTimestamp(hawkResponseHTML.getEndTimestamp());
        this.plotGNUData(plottingData);
        
        List<PlottingData> l2 = this.createPlotData(moduleMap);
        this.plotGNUData(l2);
        hawkResponseHTML.setModuleCount(String.valueOf(l2.size()));
        List<PlottingData> l3 = this.createPlotData(subTaskMap);
        this.plotGNUData(l3);
        hawkResponseHTML.setModuleSubTaskCount(String.valueOf(l3.size()));

        HawkImageUtil.copyLogo();
        System.out.println("path of index.html ====" + logPath + "/index.html");
        logger.info("path of index.html ====" + logPath + "/index.html");



        /*
         * REVISIT ME LATER... 13 OCT 2013
        try {

            FileUtil.writeFile(logPath + "/index.html", hawkResponseHTML.createTemplate());
        } catch (Exception ex) {
            logger.error("Could not write to index.html", ex);
            return false;
        }
        */ 


        return true;
    }

    public boolean plotGNUData(List<PlottingData> plottingDatas) {
        for (PlottingData plottingData : plottingDatas) {
            this.plotGNUData(plottingData);
        }
        return true;
    }

    public boolean plotGNUData(PlottingData plottingData) {
        /*
         * REVISIT ME LATER 13 OCT 2013
        try {

            FileUtil.writeFile(plottingData.getGnuDataFile(), plottingData.createTemplate());
        } catch (Exception ex) {
            System.out.println("error while writing {" + plottingData.getGnuDataFile() + "}" + ex.getMessage());
            logger.error("error while writing {" + plottingData.getGnuDataFile() + "}" + ex.getMessage());
            return false;
        }
        */ 
        logger.info("Plotting {" + plottingData.getGnuDataFile() + "}");
        this.createGNUPlotProcess(plottingData.getGnuDataFile());
        return true;
    }

    public boolean createGNUPlotProcess(String gnuDataFile) {

        return ProcessUtil.executeProcess("gnuplot " + gnuDataFile);

    }
    private static final Pattern PERF_DATA_PATTERN = Pattern.compile(
            "(.*)-(.*)"
            + HAWK_PERF_DATA_SEPRATOR + "(\\d{1,2}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})"
            + HAWK_PERF_DATA_SEPRATOR + "(\\d{1,2}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})"
            + HAWK_PERF_DATA_SEPRATOR + "(\\d*)"
            + HAWK_PERF_DATA_SEPRATOR + "(.*)");
}
