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

package org.hawk.html;

import java.util.Collection;

/**
 *
 * @author user
 */
public interface IHtmlFormatter {

    public String createHyperLink(Collection<String> coll) throws HTMLFormatException;
    public String formatFile(String fileName) throws HTMLFormatException;
    
    public String formatFile(String fileName , String outputFile) throws HTMLFormatException;
}
