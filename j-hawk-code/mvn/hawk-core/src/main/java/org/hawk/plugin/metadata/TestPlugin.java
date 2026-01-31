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

package org.hawk.plugin.metadata;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.*;

/**
 *
 * @author Manoranjan Sahu
 */
@XmlRootElement
public class TestPlugin extends HawkPluginMetaData{
    private String tabPath;

    public String getTabPath() {
        return tabPath;
    }

    public void setTabPath(String tabPath) {
        this.tabPath = tabPath;
    }
    
}
