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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

import org.hawk.xml.XMLUtil;

/**
 *
 * @author manosahu
 */
@XmlRootElement
public class Grammar {

    private SingleLine singleLine;

    public SingleLine getSingleLine() {
        return singleLine;
    }

    public void setSingleLine(SingleLine singleLine) {
        this.singleLine = singleLine;
    }

    public static void main(String args[]){
        try {
            Grammar grm = XMLUtil.unmarshal("D:\\gitn\\j-hawk-git-code\\j-hawk-code\\mvn\\j-hawk\\src\\main\\resources\\language\\grammar.xml", Grammar.class);
            System.out.println(grm.getSingleLine().getArrayDecl());
        } catch (Exception ex) {
            Logger.getLogger(Grammar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
