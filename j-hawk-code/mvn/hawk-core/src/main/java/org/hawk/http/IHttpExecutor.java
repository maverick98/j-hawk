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
package org.hawk.http;



/**
 *
 * @author msahu
 */
public interface IHttpExecutor extends Runnable{

    HttpResponse execute() throws Exception;
}
