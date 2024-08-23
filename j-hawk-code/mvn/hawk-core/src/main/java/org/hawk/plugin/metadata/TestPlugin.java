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

import javax.xml.bind.annotation.XmlRootElement;

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
