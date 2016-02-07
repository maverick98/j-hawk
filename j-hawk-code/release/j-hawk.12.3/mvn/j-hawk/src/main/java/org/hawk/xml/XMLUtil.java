/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.xml;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.hawk.logger.HawkLogger;
import org.hawk.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static org.hawk.constant.HawkConstant.*;


/**
 *
 * @author manoranjan
 */
public class XMLUtil {
     private static final HawkLogger logger = HawkLogger.getLogger(XMLUtil.class.getName());
     public static Document parse(String fileName)  {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        if (dbf != null) {
            try{
                DocumentBuilder db = dbf.newDocumentBuilder();
                dom = db.parse(fileName);
            }catch(Exception ex){
                logger.severe("error while reading xml file  {"+fileName+"} ..."+ex.getMessage());
                System.out.println("error while reading xml file  {"+fileName+"} ..."+ex.getMessage());
                
            }
        }
        return dom;
    }

    public static Element getElement(Document dom,String key) {
        
        Node node = dom.getElementsByTagName(key).item(0);
        if (node == null) {
            return null;
        }
        
        return (Element) node;
    }

    public static Node getChildNodeByName(Node parentNode, String nodeName) {
        NodeList nodes = parentNode.getChildNodes();
        Node childNode = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName().equals(nodeName)) {
                childNode = nodes.item(i);
                break;
            }
        }
        return childNode;
    }

    public static void main(String args[]) throws Exception{
        String file = "/home/manoranjan/jax/login.xml";
        Document dom = parse(file);
        Node f= dom.getDocumentElement();
        Map<String,Object> hawkParamMap = new LinkedHashMap<String,Object>();
        scan(f,hawkParamMap);
        System.out.println(hawkParamMap);
        //StructureDefnScript.parseStructMembers(hawkParamMap);
        
    }
    public static Map<String,Object> scan(String xmlFile){
        Document dom = parse(xmlFile);
        if(dom == null){
            return null;
        }
        Node f= dom.getDocumentElement();
        Map<String,Object> hawkParamMap = new LinkedHashMap<String,Object>();
        scan(f,hawkParamMap);
        return hawkParamMap;
    }
    public static boolean scan(Node node, Map<String,Object> hawkParamMap){
        if(node == null || hawkParamMap == null){
            return false;
        }
        if(node.hasAttributes()){
            Element element = (Element)node;

            String hawkParam = element.getAttribute(HAWKPARAM);
            if( !(StringUtil.isNullOrEmpty(hawkParam))){
                String data = element.getTextContent();
                if(data.startsWith("\n")){
                    data = data.substring(1);
                }
                if(data.endsWith("\n")){
                    data= data.substring(0,data.length());
                }
                 
                 hawkParamMap.put(element.getAttribute(hawkParam), data);
            }
            String hawkStruct = element.getAttribute(HAWKSTRUCT);
            if(!StringUtil.isNullOrEmpty(hawkStruct)){
                hawkParamMap.put(HAWKSTRUCT, hawkStruct);
            }
        }
        NodeList nodes = node.getChildNodes();
        if(nodes.getLength() == 0){
            return true;
        }
        for(int i=0;i<nodes.getLength();i++){
             scan(nodes.item(i),hawkParamMap);
        }
        return true;
    }
}
