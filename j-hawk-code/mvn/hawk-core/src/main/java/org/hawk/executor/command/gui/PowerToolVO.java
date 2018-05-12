/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.executor.command.gui;

import javafx.beans.property.SimpleStringProperty;
import org.hawk.executor.command.plugin.available.AvailablePluginHtmlJavaBean;
import org.hawk.plugin.HawkPlugin;

/**
 *
 * @author reemeeka
 */
public class PowerToolVO {

    private AvailablePluginHtmlJavaBean availablePluginHtmlJavaBean;
    private HawkPlugin hawkPlugin;
    private SimpleStringProperty name;
    private SimpleStringProperty version;
    private SimpleStringProperty creator;

    public PowerToolVO(String name, String version, String creator) {
        this.name = new SimpleStringProperty(name);
        this.version = new SimpleStringProperty(version);
        this.creator = new SimpleStringProperty(creator);
    }

    public PowerToolVO() {
        this.name = new SimpleStringProperty();
        this.version = new SimpleStringProperty();
        this.creator = new SimpleStringProperty();
    }

    public PowerToolVO(HawkPlugin hawkPlugin) {
        this(hawkPlugin.getName(), hawkPlugin.getPluginMetaData().getSoftware().getVersion().getVersion(), hawkPlugin.getPluginMetaData().getSoftware().getContributor().getName());
        this.hawkPlugin = hawkPlugin;
    }
    
    public PowerToolVO(AvailablePluginHtmlJavaBean availablePluginHtmlJavaBean){
        this(availablePluginHtmlJavaBean.getPlugin(),availablePluginHtmlJavaBean.getVersion(),availablePluginHtmlJavaBean.getAuthor());
        this.availablePluginHtmlJavaBean = availablePluginHtmlJavaBean;
    }

    public AvailablePluginHtmlJavaBean getAvailablePluginHtmlJavaBean() {
        return availablePluginHtmlJavaBean;
    }

    public void setAvailablePluginHtmlJavaBean(AvailablePluginHtmlJavaBean availablePluginHtmlJavaBean) {
        this.availablePluginHtmlJavaBean = availablePluginHtmlJavaBean;
    }
    
    

    public HawkPlugin getHawkPlugin() {
        return hawkPlugin;
    }

    public void setHawkPlugin(HawkPlugin hawkPlugin) {
        this.hawkPlugin = hawkPlugin;
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
