

package org.hawk.http;

/**
 * This stores http session information in the thread local.
 * @author msahu
 */
public class HttpSessionLocal<HawkSessionType> extends ThreadLocal<HawkSessionType>{

    @Override
    public HawkSessionType get() {
        
        return (HawkSessionType)super.get();
    }

    @Override
    protected HawkSessionType initialValue() {
        return (HawkSessionType)super.initialValue();
    }

    @Override
    public void remove() {
        super.remove();
    }

    
    @Override
    public void set(HawkSessionType value) {
        super.set(value);
    }


      
}
