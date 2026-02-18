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
package org.hawk.xml;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static org.hawk.constant.HawkConstant.HAWKPARAM;
import static org.hawk.constant.HawkConstant.HAWKSTRUCT;

import org.hawk.logger.HawkLogger;
import org.commons.string.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author manoranjan
 */
public class XMLUtil {

    private static final HawkLogger logger = HawkLogger.getLogger(XMLUtil.class.getName());

    public static Document parse(String fileName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        if (dbf != null) {
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                dom = db.parse(fileName);
            } catch (Exception ex) {
                logger.error("error while reading xml file  {" + fileName + "} ..." + ex.getMessage());
                System.out.println("error while reading xml file  {" + fileName + "} ..." + ex.getMessage());

            }
        }
        return dom;
    }

    public static String toUI(Node node) {

        StringBuilder sb = new StringBuilder();
        NodeList children = node.getChildNodes();
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {

                toUI(children.item(i), sb);
            }
        }

        return sb.toString();
    }

    private static boolean toUI(Node node, StringBuilder sb) {

        if (!node.getNodeName().equals("#text")) {

            sb.append("<");
            sb.append(node.getNodeName());
        }
        NamedNodeMap map = node.getAttributes();
        if (map != null) {
            for (int i = 0; i < map.getLength(); i++) {
                sb.append(" ");
                sb.append(map.item(i).toString());
            }
        }
        if (!node.getNodeName().equals("#text")) {
            sb.append(">");
            sb.append("\n");
        }

        if (node.getNodeValue() != null) {
            sb.append(node.getNodeValue());
            sb.append("\n");
        }
        NodeList children = node.getChildNodes();
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {

                toUI(children.item(i), sb);
            }
        }
        if (!node.getNodeName().equals("#text")) {
            sb.append("</");
            sb.append(node.getNodeName());
            sb.append(">");
            sb.append("\n");
        }
        return true;
    }

    public static Element getElement(Document dom, String key) {

        Node node = dom.getElementsByTagName(key).item(0);
        if (node == null) {
            return null;
        }

        return (Element) node;
    }

    public static Node getNode(Document dom, String key) {

        Node node = dom.getElementsByTagName(key).item(0);
        if (node == null) {
            return null;
        }

        return node;
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

    public static void main(String args[]) throws Exception {
        String file = "/home/manoranjan/jax/login.xml";
        Document dom = parse(file);
        Node f = dom.getDocumentElement();
        Map<String, Object> hawkParamMap = new LinkedHashMap<>();
        scan(f, hawkParamMap);
        System.out.println(hawkParamMap);
        //StructureDefnScript.parseStructMembers(hawkParamMap);

    }

    public static Map<String, Object> scan(String xmlFile) {
        Document dom = parse(xmlFile);
        if (dom == null) {
            return null;
        }
        Node f = dom.getDocumentElement();
        Map<String, Object> hawkParamMap = new LinkedHashMap<>();
        scan(f, hawkParamMap);
        return hawkParamMap;
    }

    public static boolean scan(Node node, Map<String, Object> hawkParamMap) {
        if (node == null || hawkParamMap == null) {
            return false;
        }
        if (node.hasAttributes()) {
            Element element = (Element) node;

            String hawkParam = element.getAttribute(HAWKPARAM);
            if (!(StringUtil.isNullOrEmpty(hawkParam))) {
                String data = element.getTextContent();
                if (data.startsWith("\n")) {
                    data = data.substring(1);
                }
                if (data.endsWith("\n")) {
                    data = data.substring(0, data.length());
                }

                hawkParamMap.put(element.getAttribute(hawkParam), data);
            }
            String hawkStruct = element.getAttribute(HAWKSTRUCT);
            if (!StringUtil.isNullOrEmpty(hawkStruct)) {
                hawkParamMap.put(HAWKSTRUCT, hawkStruct);
            }
        }
        NodeList nodes = node.getChildNodes();
        if (nodes.getLength() == 0) {
            return true;
        }
        for (int i = 0; i < nodes.getLength(); i++) {
            scan(nodes.item(i), hawkParamMap);
        }
        return true;
    }

    public static <T> T unmarshal(File xmlFile, Class<T> type) throws Exception {
        if (type == null || xmlFile == null) {
            throw new Exception("illegal args");
        }
        if (!xmlFile.exists()) {
            throw new Exception("xml file {" + xmlFile.getAbsolutePath() + "} does not exist");
        }
       
        T instance = null;
        try {


            JAXBContext jaxbContext = JAXBContext.newInstance(type);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            instance = (T) jaxbUnmarshaller.unmarshal(xmlFile);


        } catch (JAXBException ex) {
            ex.printStackTrace();
            logger.error("error while unmarshalling {" + xmlFile + "}", ex);
            throw new Exception(ex);
        }
        return instance;
    }
     public static <T> T unmarshal(InputStream xmlStream, Class<T> type) throws Exception {
        if (type == null || xmlStream == null) {
            throw new Exception("illegal args");
        }
         T instance = null;
        try {


            JAXBContext jaxbContext = JAXBContext.newInstance(type);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            instance = (T) jaxbUnmarshaller.unmarshal(xmlStream);


        } catch (JAXBException ex) {
            ex.printStackTrace();
            logger.error("error while unmarshalling ", ex);
            throw new Exception(ex);
        }
        return instance;
    }
    
    public static <T> T unmarshal(String xmlFile, Class<T> type) throws Exception {
        if (type == null || StringUtil.isNullOrEmpty(xmlFile)) {
            throw new Exception("illegal args");
        }
        return unmarshal(new File(xmlFile), type);
    }

    public static boolean marshal(Object instance, String xmlFile) throws Exception {
        if (instance == null || StringUtil.isNullOrEmpty(xmlFile)) {
            throw new Exception("illegal args");
        }
        return marshal(instance, new File(xmlFile));
    }

    public static boolean marshal(Object instance, File xmlFile) throws Exception {
        if (instance == null || xmlFile == null) {
            throw new Exception("illegal args");
        }

        boolean status = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(instance.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(instance, xmlFile);
            status = true;
        } catch (JAXBException ex) {
            throw new Exception(ex);
        }
        return status;
    }
}
