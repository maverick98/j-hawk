
package org.hawk.exception;

/**
 * This exception is thrown during hawk script compilation.
 * @see HawkException
 * @author msahu
 */
public class HawkCompilationException extends HawkException{

    private static final long serialVersionUID = 1L;

    public HawkCompilationException() {
    }

    public HawkCompilationException(Object msg) {
        super(msg.toString());
    }

    public HawkCompilationException(Throwable t) {
        super(t);
    }

    public HawkCompilationException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }

}
