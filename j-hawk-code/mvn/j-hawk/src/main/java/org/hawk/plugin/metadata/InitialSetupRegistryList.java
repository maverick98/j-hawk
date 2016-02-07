/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.plugin.metadata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.hawk.xml.XMLUtil;


/**
 *
 * @author manosahu
 */
@XmlRootElement(name = "InitialSetupRegistryList")
public class InitialSetupRegistryList {
    
    private List<InitialSetupRegistry> initialSetupRegistries = new ArrayList<InitialSetupRegistry>();

    public List<InitialSetupRegistry> getInitialSetupRegistries() {
        return initialSetupRegistries;
    }

    public void setInitialSetupRegistries(List<InitialSetupRegistry> initialSetupRegistries) {
        this.initialSetupRegistries = initialSetupRegistries;
    }
    

    
    
    

    
    
    
    
    public static void main(String args[]) throws Exception{
        InitialSetupRegistryList isrl  = new InitialSetupRegistryList();
        InitialSetupRegistry isr1 = new InitialSetupRegistry();
        isr1.setId("softlib");
        isr1.setName("Software Library");
        isr1.setNlsId("soft.id");
        isr1.setDescription("Software Library");
        isr1.setShortDesc("software library");
        isr1.setCallback("oracle.sysman.core.initialsetup.SoftLibSetupProcessorImpl");
        isr1.setIgnorable(false);
        isr1.setTaskFlowId("/WEB-INF/core/swlib/core-swlib-admin-console.xml#core-swlib-admin-console-flow");

        
        
        isrl.getInitialSetupRegistries().add(isr1);
        
           InitialSetupRegistry isr111 = new InitialSetupRegistry();
        isr111.setId("mos");
        isr111.setName("My Oracle Support");
        isr111.setNlsId("mos.id");
        isr111.setDescription("My Oracle Support");
        isr111.setShortDesc("My Oracle Support");
        isr111.setCallback("oracle.sysman.core.initialsetup.MOSSetupProcessorImpl");
        isr111.setIgnorable(false);
        isr111.setTaskFlowId("/WEB-INF/core/proxy/core-proxy-mos-region.xml#core-proxy-mos-region");

        
        
        isrl.getInitialSetupRegistries().add(isr111);
        
        InitialSetupRegistry isr11 = new InitialSetupRegistry();
        isr11.setId("testsetup");
        isr11.setName("test setup");
        isr11.setNlsId("test.id");
        isr11.setDescription("test setup");
        isr11.setShortDesc("test setup");
        isr11.setCallback("oracle.sysman.core.initialsetup.TestSetupProcessorImpl");
        isr11.setIgnorable(false);
        isr11.setTaskFlowId("/WEB-INF/core/swlib/core-swlib-admin-console.xml#core-swlib-admin-console-flow");
        DependsOn d = new DependsOn();
        List<String> ids = new ArrayList<String>();
        ids.add("softlib");
        ids.add("mos");
        d.getId().addAll(ids);

        isr11.setDependsOn(d);
        
        isrl.getInitialSetupRegistries().add(isr11);
        
        XMLUtil.marshal(isrl, "InitialSetupRegistry.xml");
       // System.out.println(FileUtil.readFile("plugin.xml"));

        
                
    }
}
class InitialSetupRegistry{
    String id;
    String name;
    String nlsId;
    String taskFlowId;
    String shortDesc;
    String description;
    boolean ignorable;
    String callback;
    DependsOn dependsOn;

    public DependsOn getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(DependsOn dependsOn) {
        this.dependsOn = dependsOn;
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNlsId() {
        return nlsId;
    }

    public void setNlsId(String nlsId) {
        this.nlsId = nlsId;
    }

    public String getTaskFlowId() {
        return taskFlowId;
    }

    public void setTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIgnorable() {
        return ignorable;
    }

    public void setIgnorable(boolean ignorable) {
        this.ignorable = ignorable;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
    
}
class DependsOn{
    List<String> id  = new ArrayList<String>();

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

   
}
