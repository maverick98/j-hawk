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
package org.hawk.lang.xml;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import static org.hawk.constant.HawkConstant.ENCODING;
import org.commons.ds.operator.OperatorEnum;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.lang.IScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.type.Variable;
import org.commons.resource.ResourceUtil;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
/**
 *
 * @version 1.0 2 May, 2010
 * @author msahu
 */
public class XMLResponseScript extends SingleLineScript implements ContentHandler,IObjectScript {

    /*
     *   Fix me .. make it Map<String,IScript>
     *   If the xml contains, hawk should treat as ArrayDeclScript.
     *   Currently it is checked if it is a list or not
     */
    private Map<String, Object> innerXMLMap = new HashMap<>();

    private static final String DELIM = OperatorEnum.REFERENCE.getOperator();

    private String currentKey = null;

    private String data = null;

    private boolean shouldStoreNow = false;


    private String out = null;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    


    public XMLResponseScript() {
    }

    public boolean containsKey(String key) {

        return this.innerXMLMap.containsKey(key);
    }

    public void put(String key, Object value) {
        this.innerXMLMap.put(key, value);
    }

    public Object get(String key) {
        return this.innerXMLMap.get(key);
    }

    @Override
    public IScript execute() throws Exception {

        return null;
    }
/*
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 

    private static XMLReader createXMLReader(){
        return null;
    }
    public static IObjectScript createScript(String response) {

        XMLResponseScript resultScript = new XMLResponseScript();

        resultScript.setOut(response);

        XMLReader xmlReader = createXMLReader();

        xmlReader.setContentHandler(resultScript);
        ByteArrayInputStream bais;
        bais = new ByteArrayInputStream(response.getBytes(Charset.forName(ENCODING)));
        InputSource iSource = new InputSource(bais);
        try {
            xmlReader.parse(iSource);
        } catch (Exception ex) {
            
            resultScript =  null;
        }finally{
            ResourceUtil.close(bais);
        }

        return resultScript;

    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName,String qName, Attributes atts) throws SAXException {

        if (currentKey == null) {

            currentKey = localName;

        } else {

            currentKey = currentKey + DELIM + localName;
        }

    }

    @Override
    public void endElement(String uri, String localName,String qName)  throws SAXException {

        if (currentKey.contains(DELIM) && shouldStoreNow) {

            store();

            currentKey = currentKey.substring(0, currentKey.lastIndexOf(DELIM));

            shouldStoreNow = false;

        } else if (currentKey.contains(DELIM)) {

            currentKey = currentKey.substring(0, currentKey.lastIndexOf(DELIM));
        }

    }

    private boolean store() {

        boolean result = false;

        String tmp = currentKey.substring(0, currentKey.lastIndexOf(DELIM));

        StringTokenizer strToken = new StringTokenizer(tmp, DELIM);

        XMLResponseScript tmpXMLResponseScript = this;

        while (strToken.hasMoreTokens()) {

            String element = strToken.nextToken();

            if (!tmpXMLResponseScript.containsKey(element)) {

                XMLResponseScript elementMap = new XMLResponseScript();

                tmpXMLResponseScript.put(element, elementMap);
            }

            tmpXMLResponseScript = (XMLResponseScript) tmpXMLResponseScript.get(element);


        }

        String key = currentKey.substring(currentKey.lastIndexOf(DELIM) + 2);

        if (tmpXMLResponseScript.containsKey(key)) {

            Object valueObj = tmpXMLResponseScript.get(key);

            List valueList = null;

            if (valueObj instanceof List) {

                valueList = (List) valueObj;

            } else {

                valueList = new ArrayList();

                valueList.add(valueObj);
            }

            valueList.add(data);

            tmpXMLResponseScript.put(key, valueList);

        } else {

            LocalVarDeclScript lvds = LocalVarDeclScript.createDummyStringScript(data);

            tmpXMLResponseScript.put(key, lvds);
        }

        result = true;

        return result;
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        StringBuilder sb = new StringBuilder();

        for (int i = start; i < (start + length); i++) {

            sb = sb.append(Character.toString(ch[i]));

        }

        data = sb.toString().trim();

        shouldStoreNow = true;
    }

    @Override
    public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

    @Override
    public String toString(){

        String result = null;
        if(this.getOut() == null || this.getOut().isEmpty()){
            result = this.innerXMLMap.toString();
        }else{
            result = this.getOut();
        }
        return result;
    }

   

    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

        Map<Object,Object> javaMap = new LinkedHashMap<>();

        

        for(Entry<String,Object> entry:this.innerXMLMap.entrySet()){

            Object value = entry.getValue();

            Map<Object,Object> innerMap = null;

            if(value instanceof XMLResponseScript){

                innerMap = ((XMLResponseScript)value).toJavaMap();

                for(Entry<Object,Object> innerEntry:innerMap.entrySet()){

                    javaMap.put(innerEntry.getKey(), innerEntry.getValue());

                }

            }else{
                javaMap.put(entry.getKey(),value);
            }

        }

        return javaMap;
    }

    @Override
    public String toUI() {
        return this.toString();
    }

    @Override
    public String mangle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passByReference() {
        return false;
    }

    
    @Override
    public Variable getVariable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVariable(Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVariableValue(Variable value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Variable getVariableValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object toJava() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    @Override
    public IObjectScript getActualObjectScript() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject refer(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject add(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject subtract(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject multiply(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject divide(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject modulus(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject equalTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject and(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject or(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
