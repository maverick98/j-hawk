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

package org.hawk.software;


import org.hawk.xml.XMLUtil;

/**
 *
 * @author manosahu
 */
   
   
public class SoftwareServiceImpl implements ISoftwareService {

    @Override
    public Software getSoftware() throws Exception {

        return XMLUtil.unmarshal(Thread.currentThread().getContextClassLoader().getResource("release/software.xml").openStream(), Software.class);
    }

    @Override
    public boolean isHigherVersion(Software software1, Software software2) throws Exception {
        
        return software1.getVersion().compareTo(software2.getVersion()) > 0;
    }

    @Override
    public boolean isLowerVersion(Software software1, Software software2) throws Exception {
        return software1.getVersion().compareTo(software2.getVersion()) < 0;
    }

    @Override
    public boolean isSameVersion(Software software1, Software software2) throws Exception {

        return software1.getVersion().compareTo(software2.getVersion()) == 0;
    }

}
