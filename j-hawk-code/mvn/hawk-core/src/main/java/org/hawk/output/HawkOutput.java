/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.output;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkOutput {

    private IHawkOutputWriter hawkOutputWriter;

    public IHawkOutputWriter getHawkOutputWriter() {
        return hawkOutputWriter;
    }

    public void setHawkOutputWriter(IHawkOutputWriter hawkOutputWriter) {
        this.hawkOutputWriter = hawkOutputWriter;
    }
    
    
    

    public boolean writeOutput(String data) {

        return hawkOutputWriter.writeOutput(data, true);
    }

    public boolean writeOutput(String data, boolean newLine) {

        return hawkOutputWriter.writeOutput(data, newLine);

    }

    public boolean writeError(String data) {
        return hawkOutputWriter.writeError(data);
    }

    public boolean writeEchoOutput(String data) {
        return hawkOutputWriter.writeEchoOutput(data);
    }

    public boolean writeEchoOutput(String data, boolean newLine) {
        return hawkOutputWriter.writeEchoOutput(data, newLine);
    }

    public boolean closeOutput() {
        return hawkOutputWriter.closeOutput();
    }

    public boolean closeError() {
        return hawkOutputWriter.closeError();

    }

    public boolean closeEchoOutput() {
        return hawkOutputWriter.closeEchoOutput();
    }

    public boolean closeAll() {

        this.closeOutput();
        this.closeError();
        this.closeEchoOutput();

        return true;
    }

}
