/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.executor.command.gui;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author reemeeka
 */
public class PowerToolVO {

    private SimpleStringProperty name;
    private SimpleStringProperty version;
    private SimpleStringProperty creator;

    public PowerToolVO(String name, String version, String creator) {
        this.name = new SimpleStringProperty(name);
        this.version = new SimpleStringProperty(version);
        this.creator = new SimpleStringProperty(creator);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getVersion() {
        return version.get();
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getCreator() {
        return creator.get();
    }

    public void setCreator(String creator) {
        this.creator.set(creator);
    }

}
