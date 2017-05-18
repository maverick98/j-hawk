package org.hawk.lang.object.matrix;

import java.util.Map;
import java.util.Set;
import org.common.di.ScanMe;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.object.VariableDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
public class MatrixScript extends SingleLineScript implements IObjectScript {

    private Variable matrixVariable;

    private String exp;

    private IMatrixNode matrixNode;

    public IMatrixNode getMatrixNode() {
        return matrixNode;
    }

    public void setMatrixNode(IMatrixNode matrixNode) {
        this.matrixNode = matrixNode;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public Variable getMatrixVariable() {
        return matrixVariable;
    }

    public void setMatrixVariable(Variable matrixVariable) {
        this.matrixVariable = matrixVariable;
    }

    @Override
    public void setVariableValue(Variable value) {
        this.matrixVariable = value;
    }

    @Override
    public Variable getVariableValue() {
        return matrixVariable;
    }

    @Override
    public String mangle() {
        return "_matrix_";
    }

    public static class Matrix extends SingleLineGrammar {

        @Override
        public String toString() {
            return "matrix" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public IObjectScript execute() throws Exception {
        MatrixIterator m = new MatrixIterator();
        m.setMatrixNode(this.getMatrixNode());
        m.iterate();
        StringDataType value = new StringDataType(this.toUI());
        this.getMatrixVariable().setValue(value);
        this.getOuterMultiLineScript().setLocalValue(this.getMatrixVariable(), this);
        return this;
    }

    @Override
    public MatrixScript createScript(Map<Integer, String> lineMatrixMatcherMap) throws Exception {

        if (lineMatrixMatcherMap == null) {
            return null;
        }

        MatrixScript matrixScript = this.createScript(lineMatrixMatcherMap.get(2), lineMatrixMatcherMap.get(1),true);
        return matrixScript;
    }
    
    public static  MatrixScript createScript(String exp, String variable, boolean shouldParse) throws Exception{
        MatrixScript matrixScript = new MatrixScript();
        matrixScript.setExp(exp);
        Variable matVariable = new Variable(VarTypeEnum.VAR, null, variable);
        matrixScript.setMatrixVariable(matVariable);
        
        if(shouldParse){
            MatrixParser matrixParser = new MatrixParser();
            MatrixNode matNode = matrixParser.parseMatrix(matrixScript.getExp(), matrixScript);
            matrixScript.setMatrixNode(matNode);
        }
        return matrixScript;
    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getMatrix().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getMatrix().getLinePattern();
    }

    @Override
    public Variable getVariable() {
        return this.matrixVariable;
    }

    @Override
    public void setVariable(Variable value) {
        this.matrixVariable = value;
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MatrixScript copy() {
        return this;
    }

    @Override
    public boolean passByReference() {
        return true;
    }

    @Override
    public String toUI() {
        return this.getMatrixNode().show();
    }

    @Override
    public boolean isProxy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObjectScript getActualObjectScript() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object toJava() throws Exception {
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
    public IHawkObject arrayBracket(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript) other;
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
        Integer idx = Integer.parseInt(index.toString());
        IMatrixNode childMatrixNode = this.getMatrixNode().getAt(idx);
        IObjectScript rtnObjectScript = null;
        IMatrixPayload matrixPayload = (IMatrixPayload) childMatrixNode.getPayload();
        if (childMatrixNode.isLeaf()) {
            rtnObjectScript = (IObjectScript) (matrixPayload).getValue();
        }else{
            MatrixScript matrixScript = this.createScript(matrixPayload.getExpression(),this.getMatrixVariable().getName()+idx,false );
            matrixScript.setMatrixNode(childMatrixNode);
            matrixScript.setOuterMultiLineScript(this.getOuterMultiLineScript());
            rtnObjectScript = matrixScript;
            
        }

        return rtnObjectScript;
    }

    @Override
    public IObject add(IObject otherObject) throws Exception {
        IObject rtnObject = null;
        if (otherObject instanceof VariableDeclScript) {
            rtnObject = this.add((VariableDeclScript) otherObject);
        }else if (otherObject instanceof MatrixScript){
            rtnObject = this.add((MatrixScript) otherObject);
        }
        return rtnObject;
    }
     public IObject add(MatrixScript otherObject) throws Exception {
        IObject rtnObject = null;
        IMatrixNode matNode = this.getMatrixNode().add(otherObject.getMatrixNode());
        MatrixScript resultScript = createScript(null, null, false);
        resultScript.setMatrixNode(matNode);
        return resultScript;

    }

    public IObject add(VariableDeclScript otherObject) throws Exception {
        IObject rtnObject = null;
        StringBuilder sb = new StringBuilder();
        sb.append(this.toUI());
        sb.append(otherObject.getVariableValue().getValue());
        rtnObject = LocalVarDeclScript.createDummyStringScript(sb.toString());
        return rtnObject;

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

    public class MatrixPayload implements IMatrixPayload {

        private String expression;

        private IObjectScript objectScript;

        @Override
        public String getExpression() {
            return expression;
        }

        @Override
        public void setExpression(String expression) {
            this.expression = expression;
        }

        @Override
        public boolean isCalculated() {
            return this.getValue() != null;
        }

        @Override
        public void setValue(IObject script) {
            this.objectScript = (IObjectScript) script;
        }

        @Override
        public IObject getValue() {
            return objectScript;
        }

        @Override
        public IMatrixPayload onVisit(IMatrixNode matrixNode) {

            try {
                IObjectScript rtnScript = evaluateLocalVariable(this.getExpression());

                //rtnScript.execute();
                this.setValue(rtnScript);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return this;
        }

        @Override
        public IMatrixPayload copy() {
            MatrixPayload copy = new MatrixPayload();
            return  copy;
        }

    }

}
