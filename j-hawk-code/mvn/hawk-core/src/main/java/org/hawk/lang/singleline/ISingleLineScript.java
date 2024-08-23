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
