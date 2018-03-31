/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.output;

/**
 *
 * @author reemeeka
 */
public interface IHawkOutputWriter {

    public boolean writeOutput(String data);

    public boolean writeOutput(String data, boolean newLine);

    public boolean writeError(String data);

    public boolean writeEchoOutput(String data);

    public boolean writeEchoOutput(String data, boolean newLine);

    public boolean closeOutput();

    public boolean closeError();

    public boolean closeEchoOutput();

}
