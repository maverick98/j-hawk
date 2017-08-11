/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
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
