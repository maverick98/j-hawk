
package org.hawk.exception;

/**
 * This is used mainly during hawk scripting interpretation.
 * @author msahu
 */
public class HawkInterpretationError extends HawkError{

    private static final long serialVersionUID = 1L;

    public HawkInterpretationError() {
    }

    public HawkInterpretationError(Object msg) {
        super(msg.toString());
    }

    public HawkInterpretationError(Throwable t) {
        super(t);
    }

    public HawkInterpretationError(Object msg, Throwable t) {
        super(msg.toString(), t);
    }
}
