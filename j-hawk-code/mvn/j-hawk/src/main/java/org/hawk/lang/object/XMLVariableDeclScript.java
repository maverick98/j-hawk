/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import static org.hawk.lang.constant.HawkLanguageKeyWord.varx;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.PostCreateScript;
import org.common.di.ScanMe;
import org.hawk.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author user
 */
@ScanMe(true)
public class XMLVariableDeclScript extends SingleLineScript implements IObjectScript{

    private static final HawkLogger logger = HawkLogger.getLogger(XMLVariableDeclScript.class.getName());
    protected static final Pattern VARIABLE_PATTERN =
            Pattern.compile("\\s*" + varx + "\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\.|\\d]*)\\s*=\\s*(.*)\\s*");
    private String localVarx;
    private String localVarxExp;
    private Variable variable;
    private Variable variableValue = null;
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
    public Variable getVariable() {
        return this.variable;
    }

    @Override
    public void setVariableValue(Variable value) {
        this.variableValue = value;
    }

    @Override
    public Variable getVariableValue() {
        return this.variableValue;
    }

    @Override
    public void setVariable(Variable value) {
        this.variable = value;
    }

    public String getLocalVarx() {
        return localVarx;
    }

    public void setLocalVarx(String localVarx) {
        this.localVarx = localVarx;
    }

    public String getLocalVarxExp() {
        return localVarxExp;
    }

    public void setLocalVarxExp(String localVarxExp) {
        this.localVarxExp = localVarxExp;
    }

  

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
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

    public static class XMLVariableDecl extends SingleLineGrammar {

        @Override
        public String toString() {
            return "XMLVariableDecl" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getXmlVariableDecl().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getXmlVariableDecl().getLinePattern();
    }
  
    
    public  XMLVariableDeclScript createScript(Map<Integer, String> lineXMLVariableMatcherMap) throws Exception{

        if (lineXMLVariableMatcherMap == null) {
            return null;
        }
        XMLVariableDeclScript script = new XMLVariableDeclScript();
        script.setLocalVarx(lineXMLVariableMatcherMap.get(1));
        script.setLocalVarxExp(lineXMLVariableMatcherMap.get(2));
        script.setVariable(new Variable(VarTypeEnum.VARX, null, script.getLocalVarx()));
        script.setVariableValue(script.getVariable());

        return script;
    }

    @PostCreateScript
    public boolean checkVariable() throws Exception {
        boolean status = false;
        this.getOuterMultiLineScript().setLocalValue(this.getVariable(), null);
        status = true;

        return status;
    }

    @Override
    public IObjectScript execute() throws Exception {
        IObjectScript result;
        result = this.evaluateLocalVariable(this.getLocalVarxExp());
        this.setDoc(XMLUtil.parse(result.getVariableValue().getValue().toString()));
        this.setCurrentNode(this.getDoc().getDocumentElement());
        this.outerMultiLineScript.setLocalValue(this.variable, this);
        if(result.getVariableValue() != null && this.variable != null){
            this.variable.setValue(result.getVariableValue().getValue());
        }
        return this;
    }

    @Override
    public String mangle() {

        return "_" + varx + "_";
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
