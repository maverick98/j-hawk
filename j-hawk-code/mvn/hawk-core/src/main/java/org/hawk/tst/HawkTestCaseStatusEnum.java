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

package org.hawk.tst;

/**
 *
 * @author user
 */
public enum HawkTestCaseStatusEnum {

    PASS("PASS"), FAIL("FAIL"),COMPILATION_FAILED("Compilation failed");
    
    private String displayName;

    private HawkTestCaseStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
    
    
    
}
