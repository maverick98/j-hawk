package org.hawk.data.perf;

import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.logger.HawkLogger;
import java.util.Iterator;
import org.hawk.data.perf.HawkPerfData.PerfDataCmpEnum;
import java.text.ParseException;
import org.hawk.data.perf.plotter.HawkPlotter;
import org.hawk.exception.HawkException;
import org.hawk.module.IModule;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hawk.constant.HawkConstant.*;

/**
 * This collects the perf data from all the modules execution.
 * @VERSION 1.0 6 Apr, 2010
 * @author msahu
 */
public class HawkPerfDataCollector {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkPerfDataCollector.class.getName());
    public static Map<String, List<HawkPerfData>> moduleMap = new HashMap<String, List<HawkPerfData>>();
    public static Map<String, List<HawkPerfData>> moduleTaskMap = new HashMap<String, List<HawkPerfData>>();
    public static List<HawkPerfData> all = null;
    private static final int DUMPSIZE = 500;
    private static final String PERF_DATA_FILE = "perf.data";
    private static List<HawkPerfData> allPerfData = new ArrayList<HawkPerfData>();
    private static ThreadLocal<HawkPerfData> currentPerfDataLocal = new ThreadLocal<HawkPerfData>();

    static {

        BuildModeEnum buildMode = ScriptUsage.getInstance().getBuildMode();

        if (buildMode == null || buildMode != BuildModeEnum.PLOTTING) {

            File f = new File(getPerfDataFilePath());

            if (f.exists()) {

                f.delete();

            }

            try {

                String logPath = System.getProperty("LOGPATH");

                File logDir = (new File(logPath));

                logDir.mkdirs();

                f.createNewFile();

            } catch (IOException ex) {

                logger.severe("Could not create perf.data", ex);

            }

        }

    }

    private static boolean resetCurrentPerfData() {

        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        if (currentPerfData != null) {

            currentPerfData = null;

            currentPerfDataLocal.set(currentPerfData);

            return true;

        }

        return false;
    }

    private static boolean resetCollector() {

        allPerfData = new ArrayList<HawkPerfData>();

        resetCurrentPerfData();

        return true;
    }

    private static boolean createPerfData() {

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
     * @return
     */
    public static boolean isStarted() {

        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        return currentPerfData != null;
    }

    /**
     * This indicates whether the current module is ended or not.
     * @return
     */
    public static boolean isEnded() {

        HawkPerfData currentPerfData = currentPerfDataLocal.get();

        return currentPerfData == null;
    }

    /**
     * This starts perf data collection
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully stared otherwise <tt>false</tt>.
     */
    public static boolean start(IModule module, String moduleSubTask) {

        if (isEnded()) {

            createPerfData();

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            return currentPerfData.start(module, moduleSubTask);

        }


        return false;
    }

    /**
     * This starts perf data collection
     * Note: Here the module's default task "NONE" is considered as subtask.
     * @param module
     * @return returns <tt>true</tt> if it is successfully stared otherwise <tt>false</tt>.
     */
    public static boolean start(IModule module) {

        if (isEnded()) {

            createPerfData();

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            return currentPerfData.start(module, "NONE");

        }

        return false;
    }

    /**
     * This ends perf data collection
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully ended otherwise <tt>false</tt>.
     */
    public static boolean end(IModule module, String moduleSubTask) {

        if (isStarted()) {

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            boolean status = currentPerfData.end(module, moduleSubTask);

            allPerfData.add(currentPerfData);

            dump();

            resetCurrentPerfData();

            return status;

        }

        return false;
    }

    /**
     * This ends perf data collection
     * Note: Here the module's default task "NONE" is considered as subtask.
     * @param module
     * @return returns <tt>true</tt> if it is successfully ended otherwise <tt>false</tt>.
     */
    public static boolean end(IModule module) {

        if (isStarted()) {

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            boolean status = currentPerfData.end(module, "NONE");

            allPerfData.add(currentPerfData);

            dump();

            resetCurrentPerfData();

            return status;

        }

        return false;
    }

    /**
     * This ends perf data collection for failure case
     * @param module
     * @param moduleSubTask
     * @return returns <tt>true</tt> if it is successfully ended otherwise <tt>false</tt>.
     */
    public static boolean endWithFailure(IModule module, String moduleSubTask) {

        if (isStarted()) {

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            boolean status = currentPerfData.endWithFailure(module, moduleSubTask);

            allPerfData.add(currentPerfData);

            dump();

            resetCurrentPerfData();

            return status;

        }

        return false;
    }

    /**
     * This ends perf data collection for failure case
     * @param module
     * @return returns <tt>true</tt> if it is successfully ended otherwise <tt>false</tt>.
     */
    public static boolean endWithFailure(IModule module) {

        if (isStarted()) {

            HawkPerfData currentPerfData = currentPerfDataLocal.get();

            boolean status = currentPerfData.endWithFailure(module, "NONE");

            allPerfData.add(currentPerfData);

            dump();

            resetCurrentPerfData();

            return status;

        }

        return false;
    }

    private static boolean shouldDump() {
        return (allPerfData.size() > DUMPSIZE);
    }

    public static String getPerfDataFilePath() {

        String logPath = System.getProperty("LOGPATH");

        String logFilePath = logPath + "/" + PERF_DATA_FILE;

        return logFilePath;
    }

    public static String getPerfDataModuleFilePath(String module) {

        String logPath = System.getProperty("LOGPATH");

        String logFilePath = logPath + "/" + module + "-" + PERF_DATA_FILE;

        return logFilePath;
    }

    public static boolean perfDataModuleFilePathExists(String module) {

        String filePath = getPerfDataModuleFilePath(module);

        File f = new File(filePath);

        return f.exists();
    }

    public static boolean perfDataExists() {

        String filePath = getPerfDataFilePath();

        File f = new File(filePath);

        return f.exists();
    }

    public static boolean dump() {

        if (shouldDump()) {

            String logFilePath = getPerfDataFilePath();

            if (writePerfData(allPerfData, logFilePath)) {

                resetCollector();

                return true;

            }

        }

        return false;

    }

    public static boolean dump(boolean shouldDump) {

        if (shouldDump) {

            String logFilePath = getPerfDataFilePath();

            if (writePerfData(allPerfData, logFilePath)) {

                resetCollector();

                return true;

            }

        }

        return false;

    }

    public static boolean writePerfData(List<HawkPerfData> all, String logFilePath) {



        BufferedWriter writer = null;

        try {

            writer = new BufferedWriter(new FileWriter(logFilePath, true));

            for (HawkPerfData perfData : all) {

                writer.write(perfData.getData());

            }

        } catch (Exception ex) {

            logger.severe("Could not write perf data", ex);

            return false;

        } finally {

            if (writer != null) {

                try {

                    writer.close();

                } catch (IOException ex) {

                    logger.severe("Could not close writer", ex);

                    return false;

                }

            }
        }

        return true;
    }

    public static List<HawkPerfData> readPerfData() throws HawkException {

        if (!perfDataExists()) {

            throw new HawkException("could not find perf.data file ....");

        }

        List<HawkPerfData> all = new ArrayList<HawkPerfData>();

        File perfDataFile = new File(getPerfDataFilePath());

        BufferedReader bfr = null;

        HawkPerfData perfData = null;

        try {

            bfr = new BufferedReader(new InputStreamReader(new FileInputStream(perfDataFile)));

            String line = null;

            do {

                line = bfr.readLine();

                if (line == null) {

                    break;

                }

                Matcher matcher = PERF_DATA_PATTERN.matcher(line);

                if (matcher.matches()) {

                    String module = matcher.group(1);

                    String moduleSubTask = matcher.group(2);

                    String moduleStartTime = matcher.group(3);




                    String moduleEndTime = matcher.group(4);


                    long moduleExecTime = Long.parseLong(matcher.group(5));

                    String moduleExecStatus = matcher.group(6);


                    if (perfData == null) {

                        hawkStartTime = FORMATTER.parse(moduleStartTime).getTime();

                    }

                    perfData = new HawkPerfData();

                    perfData.setModule(module);

                    perfData.setModuleSubTask(moduleSubTask);

                    perfData.setModuleStartTime(moduleStartTime);


                    perfData.setModuleEndTime(moduleEndTime);

                    perfData.setModuleExecutionTime(moduleExecTime);

                    perfData.setModuleExecutionStatus(moduleExecStatus);
                    //all.add(perfData);

                    add(all, perfData);

                }

            } while (line != null);

        } catch (Exception ex) {

            logger.severe("Could not read perf data", ex);

            return null;

        } finally {

            if (bfr != null) {

                try {

                    bfr.close();

                } catch (IOException ex) {

                    logger.severe("Could not close bfr", ex);

                }

            }

        }

        if (perfData != null) {

            try {

                hawkEndTime = FORMATTER.parse(perfData.getModuleEndTime()).getTime();

            } catch (ParseException ex) {

                logger.severe(ex);

            }
        }

        return all;
    }

    private static boolean add(List<HawkPerfData> allData, HawkPerfData thatData) {
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

    public static void main(String args[]) {
        processPerfData();
    }
    private static long hawkStartTime;
    private static long hawkEndTime;

    public static long getHawkStartTime() {
        return hawkStartTime;
    }

    public static long getHawkEndTime() {
        return hawkEndTime;
    }

    public static boolean processPerfData() {


        try {
            all = readPerfData();

        } catch (HawkException ex) {

            ex.printStackTrace();

            return false;
        }


        for (HawkPerfData perfData : all) {

            List<HawkPerfData> moduleData = moduleMap.get(perfData.getModule());

            if (moduleData == null) {

                moduleData = new ArrayList<HawkPerfData>();

            }

            moduleData.add(perfData);

            moduleMap.put(perfData.getModule(), moduleData);

        }



        Map<String, Set<String>> modules = new TreeMap<String, Set<String>>();

        for (Entry<String, List<HawkPerfData>> entry : moduleMap.entrySet()) {

            String modulePerfFilePath = getPerfDataModuleFilePath(entry.getKey());

            writePerfData(entry.getValue(), modulePerfFilePath);

            if (!modules.containsKey(entry.getKey())) {

                modules.put(entry.getKey(), new TreeSet<String>());

            }

        }


        for (HawkPerfData perfData : all) {

            List<HawkPerfData> moduleData = moduleTaskMap.get(perfData.getModuleSubTaskName());

            if (moduleData == null) {

                moduleData = new ArrayList<HawkPerfData>();

            }

            moduleData.add(perfData);

            moduleTaskMap.put(perfData.getModuleSubTaskName(), moduleData);

            if (modules.containsKey(perfData.getModule())) {

                Set<String> tasks = modules.get(perfData.getModule());

                tasks.add(perfData.getModuleSubTaskName());

                modules.put(perfData.getModule(), tasks);
            }

        }

        for (Entry<String, List<HawkPerfData>> entry : moduleTaskMap.entrySet()) {

            String modulePerfFilePath = getPerfDataModuleFilePath(entry.getKey());

            writePerfData(entry.getValue(), modulePerfFilePath);

        }


        try {

            HawkPlotter.plot(modules);

        } catch (Exception ex) {

            System.out.println("could not plot perf data ..." + ex.getMessage());

            logger.severe("could not plot perf data..." + ex.getMessage());

        }

        return true;
    }
    private static final Pattern PERF_DATA_PATTERN = Pattern.compile(
            "(.*)-(.*)"
            + HAWK_PERF_DATA_SEPRATOR + "(\\d{1,2}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})"
            + HAWK_PERF_DATA_SEPRATOR + "(\\d{1,2}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})"
            + HAWK_PERF_DATA_SEPRATOR + "(\\d*)"
            + HAWK_PERF_DATA_SEPRATOR + "(.*)");
}
