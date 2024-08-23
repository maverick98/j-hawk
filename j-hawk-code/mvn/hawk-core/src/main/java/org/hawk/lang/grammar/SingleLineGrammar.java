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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.grammar;

import java.util.Set;
import java.util.TreeSet;
import org.hawk.lang.singleline.pattern.LinePattern;

/**
 *
 * @author manosahu
 */
public class SingleLineGrammar {

    private Integer parsingSequence;

    private Set<LinePattern> linePattern = new TreeSet<>();

    public Integer getParsingSequence() {
        return parsingSequence;
    }

    public void setParsingSequence(Integer parsingSequence) {
        this.parsingSequence = parsingSequence;
    }

    public Set<LinePattern> getLinePattern() {
        return linePattern;
    }

    public void setLinePattern(Set<LinePattern> linePattern) {
        this.linePattern = linePattern;
    }

    @Override
    public String toString() {
        return "SingleLineGrammar{" + "parsingSequence=" + parsingSequence + ", linePattern=" + linePattern + '}';
    }
    
}
