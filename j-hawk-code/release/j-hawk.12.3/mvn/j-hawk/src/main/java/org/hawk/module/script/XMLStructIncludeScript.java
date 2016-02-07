/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.module.script;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.annotation.SingleTonSetter;
import org.hawk.pattern.PatternMatcher;
import org.hawk.xml.XMLUtil;

/**
 *
 * @author manoranjan
 */
public class XMLStructIncludeScript extends AbstractIncludeScript{

    /**
     * RegEx Pattern to parse alias script
     */
    private static final Pattern XML_STRUCT_INCLUDE_PATTERN=Pattern.compile("\\s*#include\\s*xml-struct\\s*(.*)\\s*\"(.*)\"");

     /**
     * Reference to SingleTon instance
     */
    private static XMLStructIncludeScript instance = new XMLStructIncludeScript();
     /**
     * A map containing module's xmls
     */
    private Map<String,Map<String,Object>> xmlStructMap = new LinkedHashMap<String,Map<String,Object>>();

    public Map<String, Map<String, Object>> getXmlStructMap() {
        return xmlStructMap;
    }


    
    /**
     * Default CTOR
     */
    public XMLStructIncludeScript(){

    }

    /**
     * This returns SingleTon instance
     * @return returns SingleTon instance
     */
    public static XMLStructIncludeScript getInstance(){
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
    public static void setInstance(XMLStructIncludeScript ins){
        instance = ins;
    }
     /**
     * Getter for Alias pattern
     * @return alias pattern
     */
    @ScriptPattern
    public static Pattern getPattern(){
        return XML_STRUCT_INCLUDE_PATTERN;
    }


    public boolean store(String structName,Map<String,Object> hawkXMLStructMap) throws HawkException{
        if(this.xmlStructMap.containsKey(structName)){

            throw new HawkException ("{"+structName + "} is already used");
        }
        this.xmlStructMap.put(structName, hawkXMLStructMap);

        return true;
    }
    @CreateScript
    public static XMLStructIncludeScript createScript(Map<Integer,String> lineXMLStructMatcherMap)
        throws HawkException{

        if(lineXMLStructMatcherMap == null){
            return null;
        }
        XMLStructIncludeScript xMLStructIncludeScript =   XMLStructIncludeScript.getInstance();

        String structName = lineXMLStructMatcherMap.get(1);
        String xmlFile = lineXMLStructMatcherMap.get(2);
        Map<String,Object> hawkXMLStructMap = XMLUtil.scan(xmlFile);
        if(hawkXMLStructMap == null){
            throw new HawkException("Unable to parse struct from "+xmlFile);
        }
        xMLStructIncludeScript.store(structName, hawkXMLStructMap);

        return xMLStructIncludeScript;
    }

    public boolean parseXMLStructs(Map<Integer, String> scriptMap) throws HawkException {
        boolean status = false;
        int size = scriptMap.size();
        for(int i=0;i<size;i++){
            if(!ScriptInterpreter.getInstance().isInsideMultiLineScript(i)){
                Map<Integer,String> lineMatcherMap = PatternMatcher.match(XMLStructIncludeScript.getPattern(), scriptMap.get(i));
                if(lineMatcherMap != null){
                    XMLStructIncludeScript xMLStructIncludeScript = createScript(lineMatcherMap);
                    if(xMLStructIncludeScript != null){
                        xMLStructIncludeScript.setLineNumber(i+1);
                        xMLStructIncludeScript.setOuterMultiLineScript(null);
                        status =true;
                    }
                }
            }
        }
        return status;
    }
}
