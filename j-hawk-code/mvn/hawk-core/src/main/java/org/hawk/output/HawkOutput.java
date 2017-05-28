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
package org.hawk.output;

import java.io.File;
import java.io.PrintWriter;
import org.commons.file.FileUtil;
import org.commons.io.IOUtil;
import org.commons.resource.ResourceUtil;
import org.common.di.AppContainer;
import org.commons.event.HawkEventPayload;
import org.commons.event.callback.HawkEventCallbackRegistry;
import org.commons.event.callback.IHawkEventCallbackRegistry;
import org.commons.event.exception.HawkEventException;

import org.hawk.logger.HawkLogger;
import org.hawk.output.event.HawkErrorEvent;
import org.hawk.output.event.HawkOutputEvent;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class HawkOutput {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkOutput.class.getName());

    private PrintWriter output = null;

    private PrintWriter error = null;

    private PrintWriter echoOutput = null;

    private File outputFile = null;

    private File errorFile = null;

    private File echoFile = null;

    public IHawkEventCallbackRegistry getHawkPluginCallbackRegistry() {
        return AppContainer.getInstance().getBean(HawkEventCallbackRegistry.class);
        //return hawkPluginCallbackRegistry;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getErrorFile() {
        return errorFile;
    }

    public void setErrorFile(File errorFile) {
        this.errorFile = errorFile;
        
            this.setError(FileUtil.createPrintWriter(errorFile));
       
    }

    public File getEchoFile() {
        return echoFile;
    }

    public void setEchoFile(File echoFile) {
        this.echoFile = echoFile;
        
            this.setEchoOutput(FileUtil.createPrintWriter(echoFile));
        
    }

    public PrintWriter getEchoOutput() {
        return echoOutput;
    }

    public void setEchoOutput(PrintWriter echoOutput) {
        this.echoOutput = echoOutput;
    }

    public PrintWriter getOutput() {
        return output;
    }

    public void setOutput(PrintWriter hawkOutput) {
        this.output = hawkOutput;
    }

    public PrintWriter getError() {
        return error;
    }

    public void setError(PrintWriter hawkError) {
        this.error = hawkError;
    }

    public boolean isOutputOverridden() {
        return this.getOutputFile()!= null;
    }

    public boolean isErrorOverridden() {
        return this.getErrorFile()!= null;
    }

    public boolean isEchoOutputOverridden() {
        return this.getEchoFile()!= null;
    }

    public boolean writeOutput(String data) {

       return writeOutput(data, true);
    }
    public boolean writeOutput(String data , boolean newLine) {

        if (this.isOutputOverridden()) {
            IOUtil.writeToPrintWriter(this.getOutput(), data);
        } else {
            if(newLine){
            System.out.println(data);
            }else{
                System.out.print(data);
            }
        }
        HawkEventPayload hawkEventPayload = new HawkEventPayload();
        hawkEventPayload.setPayload(data);
        try {
            this.getHawkPluginCallbackRegistry().dispatch(HawkOutputEvent.class, hawkEventPayload);
        } catch (HawkEventException ex) {
            ex.printStackTrace();
            System.out.println("Very unfortunate exception occurred. feeling sorry for you");
        }
        return true;
    }

    public boolean writeError(String data) {
        boolean result;
        if (this.isErrorOverridden()) {
            result = IOUtil.writeToPrintWriter(this.getError(), data);
        } else {
            result = this.writeOutput(data);
        }
        HawkEventPayload hawkEventPayload = new HawkEventPayload();
        hawkEventPayload.setPayload(data);
        try {
            this.getHawkPluginCallbackRegistry().dispatch(HawkErrorEvent.class, hawkEventPayload);
        } catch (HawkEventException ex) {
            ex.printStackTrace();
            System.out.println("Very unfortunate exception occurred. feeling sorry for you");
        }
        return result;
    }

    public boolean writeEchoOutput(String data) {
        boolean result;
        if (this.isEchoOutputOverridden()) {

            result = IOUtil.writeToPrintWriter(this.getEchoOutput(), data);
          //  this.closeEchoOutput();
        } else {
            result = this.writeOutput(data);
        }
        return result;
    }
    public boolean writeEchoOutput(String data , boolean newLine) {
        boolean result;
        if (this.isEchoOutputOverridden()) {

            result = IOUtil.writeToPrintWriter(this.getEchoOutput(), data);
          //  this.closeEchoOutput();
        } else {
            result = this.writeOutput(data,newLine);
        }
        return result;
    }

    public boolean closeOutput() {
        return ResourceUtil.close(this.getOutput());
    }

    public boolean closeError() {
        boolean result;
        if (this.isErrorOverridden()) {
            result = ResourceUtil.close(this.getError());
        } else {
            result = this.closeOutput();
        }
        return result;

    }

    public boolean closeEchoOutput() {
        boolean result;
        if (this.isEchoOutputOverridden()) {
            result = ResourceUtil.close(this.getEchoOutput());
        } else {
            result = this.closeOutput();
        }
        return result;
    }

    public boolean closeAll() {

        this.closeOutput();
        this.closeError();
        this.closeEchoOutput();

        return true;
    }

}
