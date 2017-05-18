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

import static org.hawk.constant.HawkConstant.LOGPATH;
import org.hawk.data.perf.PerfDataProcessor;

import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.logger.HawkLogger;
import org.hawk.main.HawkTargetSetting;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This plots the graph of modoules against their's response time. Note: This
 * requires <tt>gnuplot</tt> is installed on the system where hawk is runnnig.
 *
 * @VERSION 1.0 14 Apr, 2010
 * @author msahu
 */
//@Component(PLOTTINGEXECUTOR)
//@Qualifier(DEFAULTQUALIFIER)
public class PlottingExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(PlottingExecutor.class.getName());
    
    @Autowired(required = true)
    //@Qualifier(HAWKTARGETSETTING)
    private HawkTargetSetting hawkTargetSetting;
    
    @Autowired(required = true)
    //@Qualifier(HAWKPERFDATACOLLECTOR)
    private PerfDataProcessor perfDataProcessor;

    public HawkTargetSetting getHawkTargetSetting() {
        return hawkTargetSetting;
    }

    public void setHawkTargetSetting(HawkTargetSetting hawkTargetSetting) {
        this.hawkTargetSetting = hawkTargetSetting;
    }

    public PerfDataProcessor getPerfDataProcessor() {
        return perfDataProcessor;
    }

    public void setPerfDataProcessor(PerfDataProcessor perfDataProcessor) {
        this.perfDataProcessor = perfDataProcessor;
    }
    
    

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        String logPath = this.getHawkTargetSetting().getProperty(LOGPATH);
        if (logPath == null || logPath.isEmpty()) {
            logger.warn("LOGPATH is not set... Current directory will be used for plotting");
            System.out.println("LOGPATH is not set... Current directory will be used for plotting ...");
            System.setProperty("LOGPATH", ".");
        }
        
        this.getPerfDataProcessor().processPerfData();
        return true;
    }

    /**
     * This plots the graph of modoules against their's response time. Note:
     * This requires <tt>gnuplot</tt> is installed on the system where hawk is
     * runnnig.
     *
     * @return <tt> true</tt> on success , otherwise <tt>false</tt>
     */
    /*
    public boolean plot(Map<String, Set<String>> modules) throws Exception {
        PerfDataProcessor hawkPerfDataCollector = AppContainer.getInstance().getBean(HAWKPERFDATACOLLECTOR, PerfDataProcessor.class);
        String perfDataFile = hawkPerfDataCollector.getPerfDataFilePath();
        logger.info("perf data file {" + perfDataFile + "}");

        String hawkConfPath = System.getProperty(HAWKCONF);

        String targetApp = AppContainer.getInstance().getBean(HAWKMODULEPROPERTIESMANAGER, HawkModulePropertiesManager.class).getTargetApp();
        if (targetApp == null) {
            targetApp = "dummy";
        }



        String responseHTML;
        try {
            responseHTML = FileUtil.readFile("conf/hawkresponse.html", true);
        } catch (Exception ex) {
            System.out.println("Could not read hawkresponse.html ..." + ex.getMessage());
            logger.error("Could not read hawkresponse.html ..." + ex.getMessage());
            return false;
        }

        List<HawkPerfData> allboundary = HawkPerfUtil.fetchBoundaryPerfData(hawkPerfDataCollector.getAll());

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

        //responseHTML = replaceAll(responseHTML, hawkResponseHTML);

        logger.info("Plotting {" + targetApp + "} using data file {" + perfDataFile + "}");
        plotInternal(targetApp, perfDataFile, hawkResponseHTML.getStartTimestamp(), hawkResponseHTML.getEndTimestamp());

        List<String> moduleNames = new ArrayList<String>();
        for (String module : modules.keySet()) {
            if (hawkPerfDataCollector.perfDataModuleFilePathExists(module)) {
                String modulePerfFilePath = hawkPerfDataCollector.getPerfDataModuleFilePath(module);
                List<HawkPerfData> all = hawkPerfDataCollector.getModulePerfData(module);
                String startTime;
                String endTime;
                List<String> boundary = HawkPerfUtil.fetchBoundaryTime(all);
                startTime = boundary.get(0);
                endTime = boundary.get(1);
                plotInternal(module, modulePerfFilePath, startTime, endTime);
                responseHTML = responseHTML + IMAGEATTR;
             //   hawkResponseHTML.setModuleName(module);
                moduleNames.add(module);
         ///       responseHTML = replaceAll(responseHTML, hawkResponseHTML);
            }
        }


        for (Entry<String, Set<String>> entry : modules.entrySet()) {

            for (String subTask : entry.getValue()) {

                

                String modulePerfFilePath = hawkPerfDataCollector.getPerfDataModuleFilePath(subTask);
                List<HawkPerfData> all = hawkPerfDataCollector.getModuleTaskPerfData(subTask);
                String startTime;
                String endTime;
                List<String> boundary = HawkPerfUtil.fetchBoundaryTime(all);
                startTime = boundary.get(0);
                endTime = boundary.get(1);
                plotInternal(subTask, modulePerfFilePath, startTime, endTime);
                if (!responseHTML.contains(subTask)) {
                    responseHTML = responseHTML + IMAGEATTR;
                    hawkResponseHTML.setModuleName(subTask);
                    responseHTML = replaceAll(responseHTML, hawkResponseHTML);
                }

            }

        }

        HawkImageUtil.copyLogo();
        System.out.println("path of index.html ====" + logPath + "/index.html");
        logger.info("path of index.html ====" + logPath + "/index.html");

        try {

            FileUtil.writeFile(logPath + "/index.html", responseHTML);
        } catch (Exception ex) {
            logger.error("Could not write to index.html", ex);
            return false;
        }


        return true;
    }
    */

   
}
