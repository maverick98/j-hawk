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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manoranjan Sahu
 */
public class Classpath {

    private List<Jar> jar = new ArrayList<>();

    public List<Jar> getJar() {
        return jar;
    }

    public void setJar(List<Jar> jar) {
        this.jar = jar;
    }
    
}
