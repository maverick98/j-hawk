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

/**
 *
 * @author Manoranjan Sahu
 */
public class HTMLFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public HTMLFormatException() {
    }

    public HTMLFormatException(Object msg) {
        super(msg.toString());
    }

    public HTMLFormatException(Throwable t) {
        super(t);
    }

    public HTMLFormatException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
