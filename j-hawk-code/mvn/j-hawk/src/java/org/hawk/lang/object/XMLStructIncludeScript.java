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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.hawk.executor.cache.multiline.MultiLineScriptCacheFactory;
import org.hawk.lang.singleline.SingleLineScript;
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
    private Map<String,Map<String,Object>> xmlStructMap = new LinkedHashMap<>();

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
    
    public  Pattern getPattern(){
        return XML_STRUCT_INCLUDE_PATTERN;
    }

    public boolean reset(){
        this.xmlStructMap.clear();
        return true;
    }
    public boolean store(String structName,Map<String,Object> hawkXMLStructMap) throws Exception{
        if(this.xmlStructMap.containsKey(structName)){

            throw new Exception ("{"+structName + "} is already used");
        }
        this.xmlStructMap.put(structName, hawkXMLStructMap);

        return true;
    }
    
    @Override
    public  XMLStructIncludeScript createScript(Map<Integer,String> lineXMLStructMatcherMap)
        throws Exception{

        if(lineXMLStructMatcherMap == null){
            return null;
        }
        XMLStructIncludeScript xMLStructIncludeScript =   XMLStructIncludeScript.getInstance();

        String structName = lineXMLStructMatcherMap.get(1);
        String xmlFile = lineXMLStructMatcherMap.get(2);
        Map<String,Object> hawkXMLStructMap = XMLUtil.scan(xmlFile);
        if(hawkXMLStructMap == null){
            throw new Exception("Unable to parse struct from "+xmlFile);
        }
        xMLStructIncludeScript.store(structName, hawkXMLStructMap);

        return xMLStructIncludeScript;
    }

    public boolean parseXMLStructs(Map<Integer, String> scriptMap) throws Exception {
        boolean status = false;
        int size = scriptMap.size();
        for(int i=0;i<size;i++){
            if(!MultiLineScriptCacheFactory.isInsideMultiLineScript(i)){
                Map<Integer,String> lineMatcherMap = PatternMatcher.match(new XMLStructIncludeScript().getPattern(), scriptMap.get(i));
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
