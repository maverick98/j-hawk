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



/**
 *
 * @author manosahu
 */
public interface ISoftwareService {

    public Software getSoftware() throws Exception;
    
    public boolean isHigherVersion(Software software1,Software software2) throws Exception;
    
    public boolean isLowerVersion(Software software1 ,Software software2) throws Exception;
    
    public boolean isSameVersion(Software software1 , Software software2) throws Exception;
}
