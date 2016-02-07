/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.exception;

/**
 * 
 * @author msahu
 */
public class HawkError extends Error{

    private static final long serialVersionUID = 1L;

    public HawkError() {
    }

    public HawkError(Object msg) {
        super(msg.toString());
    }

    public HawkError(Throwable t) {
        super(t);
    }

    public HawkError(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
