/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawkframework.hawk.rest;

/**
 *
 * @author reemeeka
 */
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.common.di.AppContainer;
import org.hawk.config.HawkConfigHelper;
import org.hawk.logger.HawkLogger;
import org.hawk.output.DefaultHawkOutputWriter;
import org.hawk.output.HawkOutput;
import org.hawk.output.IHawkOutputWriter;
import org.hawk.plugin.IPluginDeployer;
import org.hawk.plugin.PluginDeployerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
 private static final HawkLogger logger = HawkLogger.getLogger(Main.class.getName());

    private static void configure() throws IOException{
    long start = System.currentTimeMillis();
        try {
            HawkConfigHelper.configure();
        } catch (Exception ex) {
            logger.error("error while configuring hawk", ex);
            return ;
        }
       
        
        HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
        DefaultHawkOutputWriter hawkOutputWriter = new DefaultHawkOutputWriter();
        File echoFile = File.createTempFile("echo", ".output");
        hawkOutputWriter.setEchoFile(echoFile);
        hawkOutput.setHawkOutputWriter(hawkOutputWriter);
        
        long diff1 = System.currentTimeMillis() - start;
        System.out.println("Configuration took {" + diff1 + "}ms");
        long start1 = System.currentTimeMillis();
        IPluginDeployer pluginDeployer = AppContainer.getInstance().getBean(PluginDeployerImpl.class);
        try{
            pluginDeployer.deploy();
        }catch(Throwable th){
            logger.warn("failed to deploy plugins");
        }
        long diff2 = System.currentTimeMillis() - start1;
        System.out.println("deployment took {" + diff2 + "}ms");
        long diff = System.currentTimeMillis() - start;
        System.out.println("Initialization took {" + diff + "}ms");
       
    }
    public static void main(String[] args) {
     try {
         configure();
     } catch (IOException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
     }
        SpringApplication.run(Main.class, args);
    }
}
