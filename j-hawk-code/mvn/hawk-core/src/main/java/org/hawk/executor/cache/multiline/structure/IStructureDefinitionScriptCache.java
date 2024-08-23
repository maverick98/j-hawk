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
package org.hawk.executor.cache.multiline.structure;


import org.hawk.executor.cache.multiline.IMultiLineScriptCache;
import org.hawk.lang.object.StructureDefnScript;

/**
 *
 * @author user
 */
public interface IStructureDefinitionScriptCache extends IMultiLineScriptCache {

    public  StructureDefnScript findStructureDefn(String token);

    public boolean doesStructMemberExist(String structName, String structMember) throws Exception;

    public boolean doesStructExist(String structName) throws Exception;
}
