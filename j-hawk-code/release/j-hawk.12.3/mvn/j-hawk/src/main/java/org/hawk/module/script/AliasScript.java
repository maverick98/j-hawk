


package org.hawk.module.script;


import org.hawk.pattern.PatternMatcher;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.annotation.SingleTon;
import org.hawk.module.annotation.SingleTonSetter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;
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
    private Map<String,String> moduleAliases = new HashMap<String,String>();

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
    @ScriptPattern
    public static Pattern getPattern(){
        return ALIAS_PATTERN;
    }

    /**
     * This stores the module's alias.
     * @param module module
     * @param moduleAlias module's alias
     * @return returns <tt>true</tt> on success <tt>false</tt> on failure
     * @throws org.hawk.exception.HawkException
     */
    public boolean store(String module, String moduleAlias) throws HawkException{
        if(this.moduleAliases.containsKey(moduleAlias)){

            throw new HawkException ("{"+moduleAlias + "} is already used for module {"+this.moduleAliases.get(moduleAlias)+"}");
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
     * @throws org.hawk.exception.HawkException
     */
    @CreateScript
    public static AliasScript createScript(Map<Integer,String> lineAliasMatcherMap)
        throws HawkException{

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
     * @throws org.hawk.exception.HawkException
     */
    @Override
    public IScript execute() throws HawkException {

        return LocalVarDeclScript.createDummyScript();
    }
   @Override
    public boolean isVariable() {
        return false;
    }

    public boolean parseAliases(Map<Integer, String> scriptMap) throws HawkException {
        boolean status = false;
        for(int i=0;i<scriptMap.size();i++){
            if(!ScriptInterpreter.getInstance().isInsideMultiLineScript(i)){
                Map<Integer,String> lineMatcherMap = PatternMatcher.match(AliasScript.getPattern(), scriptMap.get(i));
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

}




