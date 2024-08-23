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
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.commons.ds.operand.OperandFactory;
import org.commons.ds.operator.OperatorFactory;
import org.commons.implementor.InstanceVisitable;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;


/**
 *
 * @author user
 */
public class ElementFactory {

    private static final ILogger logger = LoggerFactory.getLogger(ElementFactory.class.getName());
    private static final ElementFactory elementFactory = new ElementFactory();
    private Map parserMap = new TreeMap();

    private ElementFactory() {
    }

    public static ElementFactory getInstance() {
        return elementFactory;
    }

    public Element create() {

        Element element = null;

        return element;
    }

    private boolean addToParserMap(Node node) {
        boolean status = false;
        Map curMap = this.parserMap;
        while (node.hasMoreTokens()) {
            String token = node.nextToken();
            Integer tokenInt = Integer.parseInt(token);
            Object object = curMap.get(tokenInt);
            if (object == null) {
                Map newMap = new TreeMap();
                newMap.put(0, node);
                curMap.remove(0);
                curMap.put(tokenInt, newMap);
                curMap = newMap;
            } else if (object instanceof Map) {
                curMap = (Map) object;
            }
        }


        return status;
    }

    public void rectify(Map curMap) {
        if (curMap != null) {
            for (Iterator it = curMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();
                Object x = entry.getKey();
                Object obj = entry.getValue();
                if (obj instanceof Node) {
                } else if (obj instanceof Map) {
                    Map map = (Map) obj;
                    if (map.containsKey(0)) {
                        Node node = (Node) map.remove(0);
                        curMap.put(x, node);
                    }
                    this.rectify((Map) obj);
                }
            }

        }
    }

    public void show() {
        this.show(this.parserMap, 3);
    }

    public void show(Map curMap, int distance) {
        if (curMap != null) {
            for (Iterator it = curMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                Object obj = entry.getValue();
                if (obj instanceof Node) {
                    for (int i = 0; i < distance; i++) {
                        System.out.print("-");
                    }
                    System.out.print(">");
                    System.out.println(obj);
                } else if (obj instanceof Map) {
                    this.show((Map) obj, distance + 1);
                }

            }
        }
    }

    public boolean cacheParsingSequence() throws Exception {
        boolean status = false;

        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(Element.class);
        new ElementVisitor() {

            @Override
            public void onVisit(IElement element) {
                Node node = Node.createElementNode(element);
                addToParserMap(node);
            }

            
        }.visit(instanceVisitable);
         
        this.rectify(parserMap);
        return status;
    }

    public static void main(String args[]) throws Exception {
        ElementFactory.getInstance().cacheParsingSequence();
        String equation = "var oppAustrlia[] = `exec InningFetcher->equalOppositionName(\"Australia\")`";
        List<IElement> elements =  ElementFactory.getInstance().parseElements(equation);
        //System.out.println(ElementFactory.getInstance().parserMap);
        ElementFactory.getInstance().show();
    }

    public List<IElement> parseElements(String equation) throws Exception {
        cacheParsingSequence();
        List<IElement> elements = new ArrayList<>();
        int operatorCount = 0;
        int inputCount = 0;
        int pos = 0;

        for (int i = 0; i < equation.length();) {
            IElement element = this.breadthFirstSearch(equation, i, parserMap, pos);
            if (element != null) {
                i += element.getShiftLength();
                if (element.shouldAdd()) {
                    boolean isElementAOperator = OperatorFactory.getInstance().isOperator(element.getElement());
                    if (isElementAOperator) {
                        operatorCount++;
                    } else if (OperandFactory.getInstance().isOperand(element.getElement())) {
                        inputCount++;
                    }
                    pos++;

                    elements.add(element);
                }
            }

        }
        return inputCount == operatorCount + 1
                ? elements
                : null;

    }

    private IElement breadthFirstSearch(String equation, int i, Map curMap, int pos) {

        IElement result = null;
        char ele = equation.charAt(i);
        String eleStr = String.valueOf(ele);
        String subStr = equation.substring(i);
        if (curMap != null) {
            for (Iterator it = curMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                Object obj = entry.getValue();
                if (obj instanceof Node) {
                    Node node = (Node) obj;
                    IElement element = node.getElement();
                    if (element instanceof ClubberElement || element instanceof EmptyElement) {
                        result = element.parse(eleStr, pos);
                    } else {
                        result = element.parse(subStr, pos);
                    }

                    if (result != null) {
                        return result;
                    } else if (element instanceof ClubberElement || element instanceof EmptyElement) {
                        result = element.parse(eleStr, pos);
                    }
                    if (result != null) {
                        return result;

                    }

                } else if (obj instanceof Map) {
                    result = this.breadthFirstSearch(equation, i, (Map) obj, pos);
                    if (result != null) {
                        return result;
                    }
                }

            }
        }

        return result;


    }

    private static class Node {

        private IElement element;
        private StringTokenizer tokenizer = null;
        private static final String DELIM = ".";

        private Node() {
        }

        public static Node createElementNode(IElement element) {
            Node elementNode = new Node();
            elementNode.setElement(element);

            return elementNode;
        }

        public IElement getElement() {
            return element;
        }

        public void setElement(IElement element) {
            this.element = element;
        }

        public String getSequence() {
            return element.getHorizontalParserSequence();
        }

        public boolean isAdd() {
            return element.shouldAdd();
        }

        @Override
        public String toString() {
            return "Node{" + "element=" + element + ", sequence=" + element.getHorizontalParserSequence() + ", add=" + element.shouldAdd() + '}';
        }

        public boolean hasMoreTokens() {
            if (tokenizer == null) {
                tokenizer = new StringTokenizer(this.getSequence(), DELIM);
            }
            return tokenizer.hasMoreTokens();
        }

        public String nextToken() {
            if (tokenizer == null) {
                tokenizer = new StringTokenizer(this.getSequence(), DELIM);
            }
            String result = null;
            if (tokenizer.hasMoreTokens()) {
                result = tokenizer.nextToken();
            } else {
                result = null;
            }
            return result;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + (this.element.getHorizontalParserSequence() != null ? this.element.getHorizontalParserSequence().hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Node other = (Node) obj;
            if ((this.element.getHorizontalParserSequence() == null) ? (other.element.getHorizontalParserSequence() != null) : !this.element.getHorizontalParserSequence().equals(other.getElement().getHorizontalParserSequence())) {
                return false;
            }
            return true;
        }
    }
}
