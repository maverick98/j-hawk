/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object;

import java.util.Map;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.logger.HawkLogger;
import org.hawk.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author user
 */

public class XMLVariableDeclScript extends VARXVariableDeclProxyScript{

    private static final HawkLogger logger = HawkLogger.getLogger(XMLVariableDeclScript.class.getName());
   
   
    private Document doc;
    private Node currentNode = null;

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;

    }

   
    @Override
    public IObjectScript execute() throws Exception {
        this.setDoc(XMLUtil.parse(this.getResult().getVariableValue().getValue().toString()));
        this.setCurrentNode(this.getDoc().getDocumentElement());
        return this;
    }

    

    @Override
    public XMLVariableDeclScript copy() {
        XMLVariableDeclScript copy = new XMLVariableDeclScript();
        copy.setVariable(this.getVariable());
        copy.setVariableValue(this.getVariableValue());
        copy.setLocalVarx(this.getLocalVarx());
        copy.setLocalVarxExp(this.getLocalVarxExp());
        copy.setOuterMultiLineScript(this.getOuterMultiLineScript());
        copy.setDoc(this.getDoc());
        copy.setCurrentNode(this.getCurrentNode());
        return copy;
    }

    @Override
    public String toUI() {

        return XMLUtil.toUI(this.getCurrentNode());

    }

    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Node node = XMLUtil.getNode(this.getDoc(), otherScript.getVariable().getName());
        this.setCurrentNode(node);
        return this;
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        if (otherScript == null || otherScript.getVariable() == null || otherScript.getVariable().getValue() == null) {
            throw new Exception("null array index found...");
        }

        IDataType indexDataType = otherScript.getVariable().getValue();


        if (!(indexDataType instanceof IntDataType)) {
            throw new Exception("Invalid array index");
        }
        IntDataType index = (IntDataType) indexDataType;
        if (!index.isPositiveInteger()) {
            throw new Exception("Non integer array index access");
        }
       
        NodeList children = this.getCurrentNode().getParentNode().getChildNodes();
        for ( int i = 0 ; i <children.getLength();i++){
            System.out.println(children.item(i));
        }
        //this.setCurrentNode(children.item(index.getData()-1));
        return this;
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passByReference() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    @Override
    public Map<Object, Object> toJavaMap() throws Exception {
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
    
}
