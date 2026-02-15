package org.hawkframework.hawk.rest.util;

import java.io.File;
import java.io.IOException;
import org.common.di.AppContainer;
import org.hawk.config.HawkConfigHelper;
import org.hawk.logger.HawkLogger;
import org.hawk.output.DefaultHawkOutputWriter;
import org.hawk.output.HawkOutput;
import org.hawk.plugin.IPluginDeployer;
import org.hawk.plugin.PluginDeployerImpl;

/**
 *
 * @author reemeeka
 */
public class HawkRestConfigHelper {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkRestConfigHelper.class.getName());

    private static boolean configureJHawk() {
        long start = System.currentTimeMillis();
        try {
            HawkConfigHelper.configure();
        } catch (Exception ex) {
            logger.error("error while configuring hawk", ex);
            return false;
        }
        System.out.println("Configuration took {" + (System.currentTimeMillis() - start) + "}ms");
        return true;
    }

    public static File setJHawkOutput(String output, String extn) {

        long start = System.currentTimeMillis();
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        DefaultHawkOutputWriter hawkOutputWriter = new DefaultHawkOutputWriter();
        File echoFile;
        try {
            echoFile = hawkOutputWriter.getEchoFile();
            if(echoFile!= null){
                echoFile.delete();
            }
            echoFile = File.createTempFile(output, extn);

            hawkOutputWriter.setEchoFile(echoFile);
            hawkOutput.setHawkOutputWriter(hawkOutputWriter);
        } catch (IOException ex) {
            logger.error("error while configuring hawkouput", ex);
            return null;
        }
        System.out.println("setJHawkOutput took {" + (System.currentTimeMillis() - start) + "}ms");
        return echoFile;

    }

    private static boolean deployPlugin() {
        long beforePluginDeploy = System.currentTimeMillis();
        IPluginDeployer pluginDeployer = AppContainer.getInstance().getBean(PluginDeployerImpl.class);
        try {
            pluginDeployer.deploy();
        } catch (Throwable th) {
            logger.warn("failed to deploy plugins");
            return false;
        }

        System.out.println("deployment took {" + (System.currentTimeMillis() - beforePluginDeploy) + "}ms");
        return true;

    }

    public static boolean configure() {
        long start = System.currentTimeMillis();
        if (configureJHawk()) {
            if (setJHawkOutput("echo", "output") != null) {
                return deployPlugin();
            }
        }
        System.out.println("Initialization took {" + (System.currentTimeMillis() - start) + "}ms");
        return true;
    }
}
