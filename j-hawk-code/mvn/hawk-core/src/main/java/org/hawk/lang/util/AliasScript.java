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


package org.hawk.lang.util;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.hawk.executor.cache.multiline.MultiLineScriptCacheFactory;
import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.alias;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.module.annotation.SingleTon;
import org.hawk.module.annotation.SingleTonSetter;
import org.hawk.pattern.PatternMatcher;
/**
 * This defines alias for a target application module.
 * <p> e.g.
 * <p>{@code alias SampleAppModule = sam}<br>
 * <tt>sam</tt> can be used anywhere in the hawk script
 * instead of <tt>SampleAppModule</tt>.
 * <p>Suppose <tt>SampleAppModule</tt> has a sub task named <tt>Finish</tt><br>
 * Following statements are valid.<br>
 * <tt>SampleAppModule</tt>-><tt>Finish</tt><br>
 * <tt>sam</tt>-><tt>Finish</tt><br>
 * 
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */
@SingleTon
public class AliasScript extends SingleLineScript{

    /**
     * RegEx Pattern to parse alias script
     */
    private static final Pattern ALIAS_PATTERN=Pattern.compile("\\s*"+alias+"\\s*([\\w||\\d]*)\\s*=\\s*(\\w*)\\s*");

    /**
     * Reference to SingleTon instance
     */
    private static AliasScript instance = new AliasScript();

    /**
     * A map containing module's aliases
     */
    private Map<String,String> moduleAliases = new HashMap<>();

    /**
     * Default CTOR
     */
    public AliasScript(){
        
    }

    /**
     * This returns SingleTon instance
     * @return returns SingleTon instance
     */
    public static AliasScript getInstance(){
        return instance;
    }

    /**
     * There is only one AliasScript.
     * <tt>SingleTonSetter</tt> helps the generic
     * script generation mechanism defined in  {@link SingleLineScript}
     * @param ins
     * @see SingleLineScript
     */
    @SingleTonSetter
    public static void setInstance(AliasScript ins){
        instance = ins;
    }

    /**
     * Getter for Alias pattern
     * @return alias pattern
     */
    
    public  Pattern getPattern(){
        return ALIAS_PATTERN;
    }

    /**
     * This stores the module's alias.
     * @param module module
     * @param moduleAlias module's alias
     * @return returns <tt>true</tt> on success <tt>false</tt> on failure
     * @throws org.hawk.exception.Exception
     */
    public boolean store(String module, String moduleAlias) throws Exception{
        if(this.moduleAliases.containsKey(moduleAlias)){

            throw new Exception ("{"+moduleAlias + "} is already used for module {"+this.moduleAliases.get(moduleAlias)+"}");
        }
        this.moduleAliases.put(moduleAlias, module);

        return true;
    }
    

    /**
     * This returns module name from it's alias
     * @param moduleAlias
     * @return returns module name from it's alias
     */
    public String get(String moduleAlias){
        return this.moduleAliases.get(moduleAlias);
    }


    /**
     * This creates <tt>AliasScript</tt> from the
     * Alias matcher map. The alias matcher map is earlier computed from
     * {@link AliasScript#getPattern}
     * @param lineAliasMatcherMap
     * @return returns alias script
     * @throws org.hawk.exception.Exception
     */
    
    @Override
    public  AliasScript createScript(Map<Integer,String> lineAliasMatcherMap)
        throws Exception{

        if(lineAliasMatcherMap == null){
            return null;
        }
        AliasScript aliasScript = AliasScript.getInstance();
        
        aliasScript.store(lineAliasMatcherMap.get(2),lineAliasMatcherMap.get(1));
        
        return aliasScript;
    }


    /**
     * This executes the alias script
     * @return returns boolean
     * @throws org.hawk.exception.Exception
     */
    @Override
    public IScript execute() throws Exception {

        return LocalVarDeclScript.createDummyBooleanScript();
    }
    /*
   @Override
    public boolean isVariable() {
        return false;
    }
    */ 

    public boolean parseAliases(Map<Integer, String> scriptMap) throws Exception {
        boolean status = false;
        for(int i=0;i<scriptMap.size();i++){
            if(!MultiLineScriptCacheFactory.isInsideMultiLineScript(i)){
                Map<Integer,String> lineMatcherMap = PatternMatcher.match(this.getPattern(), scriptMap.get(i));
                if(lineMatcherMap != null){
                    AliasScript aliasScript = createScript(lineMatcherMap);
                    if(aliasScript != null){
                        aliasScript.setLineNumber(i+1);
                        aliasScript.setOuterMultiLineScript(null);
                        status =true;
                    }
                }
            }
        }
        return status;
    }

    public boolean reset() {
        moduleAliases = new HashMap<>();
        return true;
    }

}




