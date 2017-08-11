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
