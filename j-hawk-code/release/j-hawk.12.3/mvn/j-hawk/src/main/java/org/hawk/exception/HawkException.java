package org.hawk.exception;


/**
 * Hawk Exception.
 * @author msahu
 */
public class HawkException extends Exception {

    private static final long serialVersionUID = 1L;

    public HawkException() {
    }

    public HawkException(Object msg) {
        super(msg.toString());
    }

    public HawkException(Throwable t) {
        super(t);
    }

    public HawkException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }

   
}
