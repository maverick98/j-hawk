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
package org.hawk.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hawk.config.HawkConfigHelper;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkCommandParser;
import org.hawk.executor.command.interpreter.ScriptInterpretationPerfCommand;
import org.hawk.executor.command.gui.GUIHawkMain;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.IPluginDeployer;
import org.hawk.plugin.PluginDeployerImpl;
/**
 *
 * @author Manoranjan Sahu
 */
public class CommandLineHawkMain {

    private static final HawkLogger logger = HawkLogger.getLogger(CommandLineHawkMain.class.getName());

    /**
     * Entry point of hawk
     *
     * @param args command line arguments as per java standards
     */
    public static void main(String args[]) {
        int exitCode;
        //  ThreadDumpAnalyzer tda = new ThreadDumpAnalyzer();
        //  tda.startScheduleTask();
        try {

            boolean executionStatus = execute(args);
            if (executionStatus) {
                exitCode = 0;
            } else {
                exitCode = -1;
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
               
    }

    public static boolean execute(String args[]) throws Exception {
        long start = System.currentTimeMillis();
        try {
            HawkConfigHelper.configure();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("error while configuring hawk", ex);
            throw new Exception(ex);
        }
       
        
        
        /*
        long diff1 = System.currentTimeMillis() - start;
        System.out.println("Configuration took {" + diff1 + "}ms");
        long start1 = System.currentTimeMillis();
        IPluginDeployer pluginDeployer = AppContainer.getInstance().getBean(PluginDeployerImpl.class);
        pluginDeployer.deploy();
        long diff2 = System.currentTimeMillis() - start1;
        System.out.println("deployment took {" + diff2 + "}ms");
        long diff = System.currentTimeMillis() - start;
        System.out.println("Initialization took {" + diff + "}ms");
                */
        boolean status;
        HawkCommandParser hawhCommandParser = AppContainer.getInstance().getBean(HawkCommandParser.class);
        try {

            status = hawhCommandParser.parseArguments(args);

        } catch (Exception ex) {
            logger.error("error while parsing argunts", ex);
            throw new Exception(ex);
        }
        boolean result = false;
        if (status) {
            try {
                result = hawhCommandParser.getHawkExecutionCommand().execute();
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("error while executing command ", ex);
                throw new Exception(ex);
            }
        } else {
            logger.info("Invalid option");
        }

        return result;

    }
}
