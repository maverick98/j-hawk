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

package org.commons.string;

/**
 *
 * @author manosahu
 */
public class ParserException extends Exception{

    private static final long serialVersionUID = 1L;

    public ParserException() {
    }

    public ParserException(Object msg) {
        super(msg.toString());
    }

    public ParserException(Throwable t) {
        super(t);
    }

    public ParserException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }

}
