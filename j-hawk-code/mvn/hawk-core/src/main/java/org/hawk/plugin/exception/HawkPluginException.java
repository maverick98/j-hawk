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
package org.hawk.plugin.exception;



/**
 * This exception is thrown during hawk script compilation.
 *
 * @see Exception
 * @author msahu
 */
public class HawkPluginException extends Exception {

    private static final long serialVersionUID = 1L;

    public HawkPluginException() {
    }

    public HawkPluginException(Object msg) {
        super(msg.toString());
    }

    public HawkPluginException(Throwable t) {
        super(t);
    }

    public HawkPluginException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
