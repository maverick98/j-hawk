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
package org.hawk.lang.singleline;

import java.util.Map;
import java.util.Set;

import org.hawk.lang.IScript;
import org.hawk.lang.singleline.pattern.LinePattern;

/**
 *
 * @author user
 */
public interface ISingleLineScript extends IScript {

    public IScript createScript(Map<Integer, String> lineMatcherMap) throws Exception;

    public Set<LinePattern> getPatterns();
}
