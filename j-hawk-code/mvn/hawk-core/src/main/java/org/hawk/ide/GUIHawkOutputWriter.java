/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.ide;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.hawk.output.IHawkOutputWriter;

/**
 *
 * @author reemeeka
 */
public class GUIHawkOutputWriter implements IHawkOutputWriter {

    private TextArea outputArea;

    public GUIHawkOutputWriter(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    @Override
    public boolean writeOutput(String data) {
        return this.writeOutput(data, true);
    }
    
    public void clear(){
         Platform.runLater(() -> {
            outputArea.deleteText(0, outputArea.getLength());
        });
    }

    @Override
    public boolean writeOutput(String data, boolean newLine) {
        
         Platform.runLater(() -> {
             if(newLine){
                 outputArea.appendText("\n");
             }
            outputArea.appendText(data);
        });
         return true;
    }

    @Override
    public boolean writeError(String data) {
        return this.writeOutput(data, true);
    }

    @Override
    public boolean writeEchoOutput(String data) {
        return this.writeOutput(data, true);
    }

    @Override
    public boolean writeEchoOutput(String data, boolean newLine) {
        return this.writeOutput(data, true);
    }

    @Override
    public boolean closeOutput() {
        return false;
    }

    @Override
    public boolean closeError() {
       return false;
    }

    @Override
    public boolean closeEchoOutput() {
        return false;
    }

}
