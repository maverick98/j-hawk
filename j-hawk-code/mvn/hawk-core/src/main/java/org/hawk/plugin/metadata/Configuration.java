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

/**
 *
 * @author Manoranjan Sahu
 */
public class Configuration {

    /**
     * path to config xml file a plugin may have
     */
    private String configXML;
    
    /**
     * this is the parser to above configXML field
     */
    private String parserClazz;
    
    
    private String hawkConfigClazz;
    
    private String springConfigClazz;

    public String getSpringConfigClazz() {
        return springConfigClazz;
    }

    public void setSpringConfigClazz(String springConfigClazz) {
        this.springConfigClazz = springConfigClazz;
    }
    
    
    

    public String getHawkConfigClazz() {
        return hawkConfigClazz;
    }

    public void setHawkConfigClazz(String hawkConfigClazz) {
        this.hawkConfigClazz = hawkConfigClazz;
    }

    public String getConfigXML() {
        return configXML;
    }

    public void setConfigXML(String configXML) {
        this.configXML = configXML;
    }

    public String getParserClazz() {
        return parserClazz;
    }

    public void setParserClazz(String parserClazz) {
        this.parserClazz = parserClazz;
    }
    
    
}
