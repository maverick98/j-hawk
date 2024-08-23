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
