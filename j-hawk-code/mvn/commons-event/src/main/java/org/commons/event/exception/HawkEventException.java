/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 *  
 *
 * 
 */


package org.commons.event.exception;

/**
 *
 * @author manosahu
 */
public class HawkEventException  extends Exception {

    private static final long serialVersionUID = 1L;

    public HawkEventException() {
    }

    public HawkEventException(Object msg) {
        super(msg.toString());
    }

    public HawkEventException(Throwable t) {
        super(t);
    }

    public HawkEventException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
