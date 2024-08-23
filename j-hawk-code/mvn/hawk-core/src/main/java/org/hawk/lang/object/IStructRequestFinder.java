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


package org.hawk.lang.object;

import java.util.Map;


/**
 *
 * @author manoranjan
 */
public interface IStructRequestFinder {

     String find(Object mapObj, String key) throws Exception ;

     Map<String, String> findMap(Object mapObj) throws Exception ;
}
