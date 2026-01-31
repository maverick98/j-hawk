/*
 * This file is part of j-hawk
 *  
 *
 * 
 */
package org.commons.xml;

import java.io.File;
import java.io.InputStream;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author manosahu
 */
public class XMLUtil {

    private static final ILogger logger = LoggerFactory.getLogger(XMLUtil.class.getName());

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

    public static <T> T unmarshal(File xmlFile, Class<T> type) {
        if (type == null || xmlFile == null) {
            return null;
        }
        if (!xmlFile.exists()) {
            return null;
        }

        T instance = null;
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(type);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            instance = (T) jaxbUnmarshaller.unmarshal(xmlFile);

        } catch (JAXBException ex) {
            ex.printStackTrace();
            logger.error("error while unmarshalling {" + xmlFile + "}", ex);

        }
        return instance;
    }

    public static <T> T unmarshal(InputStream xmlStream, Class<T> type) {
        if (type == null || xmlStream == null) {
            return null;
        }
        T instance = null;
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(type);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            instance = (T) jaxbUnmarshaller.unmarshal(xmlStream);

        } catch (JAXBException ex) {
            ex.printStackTrace();
            logger.error("error while unmarshalling ", ex);

        }
        return instance;
    }

    public static <T> T unmarshal(String xmlFile, Class<T> type) {
        if (type == null || StringUtil.isNullOrEmpty(xmlFile)) {
            return null;
        }
        return unmarshal(new File(xmlFile), type);
    }

    public static boolean marshal(Object instance, String xmlFile) {
        if (instance == null || StringUtil.isNullOrEmpty(xmlFile)) {
            return false;
        }
        return marshal(instance, new File(xmlFile));
    }

    public static boolean marshal(Object instance, File xmlFile) {
        if (instance == null || xmlFile == null) {
            return false;
        }

        boolean status = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(instance.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(instance, xmlFile);
            status = true;
        } catch (JAXBException ex) {
            ex.printStackTrace();
            return false;
        }
        return status;
    }
}
