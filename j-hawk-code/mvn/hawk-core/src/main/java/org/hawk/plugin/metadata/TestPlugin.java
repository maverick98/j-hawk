/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
