/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
package org.hawk.lang.object;

import java.util.HashMap;
import java.util.Map;
import org.commons.string.StringUtil;
import static org.hawk.constant.HawkConstant.ACTION_NAME;
import static org.hawk.constant.HawkConstant.CONCURRENT;
import static org.hawk.constant.HawkConstant.INVALIDATE_SESSION;
import static org.hawk.constant.HawkConstant.SHOULD_DUMP;


/**
 * TODO . This is not my address.
 * @author manoranjan
 */
public class ControlRequest {
    private boolean dump = true;
    private String actionName;
    private boolean concurrent;
    private boolean invalidateSession;
    private Map hawkMap = new HashMap();
    private ControlRequestFinder controlRequestFinder = new ControlRequestFinder();

    public boolean isDump() {
        return dump;
    }

    public void setDump(boolean dump) {
        this.dump = dump;
    }
    
    public Map getHawkMap() {
        return hawkMap;
    }

    public void setHawkMap(Map hawkMap) {
        this.hawkMap = hawkMap;
    }
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    

    public boolean isInvalidateSession() {
        return invalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        this.invalidateSession = invalidateSession;
    }
    public ControlRequest(Map hawkMap) throws Exception {
        this.hawkMap = hawkMap;
        this.concurrent = this.controlRequestFinder.findConcurrency(this.hawkMap);
        this.invalidateSession = this.controlRequestFinder.findInvalidateSession(this.hawkMap);
        this.actionName = this.controlRequestFinder.findActionName(this.hawkMap);
        this.dump = this.controlRequestFinder.shouldDump(this.hawkMap);
    }
    private static class ControlRequestFinder extends AbstractStructRequestFinder {

        public String findActionName(Map mainMap) throws Exception {

            return find(mainMap, ACTION_NAME);
        }

        public boolean findInvalidateSession(Map mainMap) throws Exception {

            return "1".equals(find(mainMap, INVALIDATE_SESSION)) ;
        }
        public boolean findConcurrency(Map mainMap) throws Exception {

            return "1".equals(find(mainMap, CONCURRENT));
        }

        private boolean shouldDump(Map mainMap) throws Exception{
            String shouldDump = find(mainMap, SHOULD_DUMP);
            if(StringUtil.isNullOrEmpty(shouldDump)){
                shouldDump = "0";
            }
            return Integer.parseInt(shouldDump) == 1;
        }
    }
}
