


package org.hawk.module.script;


import org.hawk.ds.BinaryTree;
import org.hawk.ds.Element;
import org.hawk.ds.Expression;
import org.hawk.exception.HawkException;
import org.hawk.pattern.PatternMatcher;
import java.util.List;
import java.util.Map;
import org.hawk.ds.OperatorEnum;
import org.hawk.exception.HawkError;
import org.hawk.logger.HawkLogger;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.module.script.type.DoubleDataType;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.StringDataType;
import org.hawk.module.script.type.Variable;

/**
 * Abstract class for IScript.
 * This provides default implementation
 * for {@link IScript#pushLocalVars}
 * and {@link IScript#popLocalVars}
 * @version 1.0 29 Apr, 2010
 * @author msahu
 */
public abstract class AbstractScript implements IScript{

    private static final HawkLogger logger = HawkLogger.getLogger(AbstractScript.class.getName());
    protected MultiLineScript outerMultiLineScript  = null;
    
    private int lineNumber;

    private boolean lastScript = false;

    public boolean isLastScript() {
        return lastScript;
    }

    public void setLastScript(boolean lastScript) {
        this.lastScript = lastScript;
    }

    

    public MultiLineScript getOuterMultiLineScript() {
        return outerMultiLineScript;
    }

    public void setOuterMultiLineScript(MultiLineScript outerMultiLineScript) {
        this.outerMultiLineScript = outerMultiLineScript;
    }

    

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    

    public abstract IScript execute() throws HawkException;

    /**
     *Default implementation for {@link IScript#pushLocalVars}
     */
    public void pushLocalVars(){return;}

    /**
     *Default implementation for {@link IScript#popLocalVars}
     */
    public Map<String,IDataType> popLocalVars(){return null;}

    /**
     * 
     * @return
     */
    public abstract boolean isVariable();

    public String mangle(){
        throw new HawkError("Unsupported operation");
    }

    protected abstract  MultiLineScript findNearestOuterMLScript();

    protected FunctionScript findEncapsulatingFunctionScript(){
        FunctionScript result = null;
        MultiLineScript mls = this.getOuterMultiLineScript();
        do{

            
            if(mls!= null && mls.getFunctionScript()!= null){
                result = mls.getFunctionScript();
                break;
            }
            mls = mls.getOuterMultiLineScript();
        }while(mls!=null);

        return result;
    }

    public  boolean evaluateLocalBoolean(String expression) throws HawkException{
        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        
        if(expression == null || expression.isEmpty() || multiLineScript == null){
            throw new HawkException("invalid");
        }
        Variable rtn = this.evaluateLocalVariable(expression).getVariableValue();
        BooleanDataType result = (BooleanDataType)rtn.getVariableValue();
        return result.isData();
    }


    public  IScript evaluateGlobalVariable(String expression )throws HawkException{
        if(expression == null || expression.isEmpty()){
            throw new HawkException("Invalid");
        }
        IScript rtn = null;
        BinaryTree bt =  new BinaryTree(expression);
        Expression infix = bt.getInfixExpression();
        evaluateGlobalVariableInternal(infix);
        bt.createTree();
        rtn = bt.calculate();
        return rtn;
    }

    private  void evaluateGlobalVariableInternal (Expression infix )throws HawkException{

        List<Element> infixElement = infix.getExpression();
        ScriptInterpreter scriptInterpreter = ScriptInterpreter.getInstance();
        for (Element element : infixElement) {
            if (element.isOperand()) {
                if (element.isOperand() && element.isVariable()) {
                    String operand = element.toString();
                    //Fix me struct can be global var
                    Variable operandVar = new Variable(VarTypeEnum.VAR, null, operand);
                    IScript operandVarScript = null;
                    Variable operandValue = null;



                    if (scriptInterpreter.isGlobalVarDeclared(operandVar)) {
                        operandVarScript = scriptInterpreter.getGlobalValue(operand);
                    }
                    if (operandValue == null) {
                        throw new HawkException("undeclared variable {" + operand + "}");
                    } else {
                        element.setElement(operandValue + "");
                        
                        break;
                    }
                } else {
                    VariableDeclScript lvds = new VariableDeclScript();
                    Variable operandValue = new Variable(VarTypeEnum.VAR, null, element.getElement());
                    operandValue.setVariableValue(
                            element.isDouble()
                            ? new DoubleDataType(Double.parseDouble(element.getElement()))
                            : new StringDataType(element.getElement()));
                    lvds.setVariable(operandValue);
                    lvds.setVariableValue(operandValue);
                    element.setValue(lvds);
                }

            }
        }

    }


    public IScript evaluateLocalVariable(String expression) throws HawkException{

        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        if(expression == null || expression.isEmpty() || multiLineScript == null){
            throw new HawkException("invalid");
        }
        if(ScriptUsage.getInstance().getBuildMode() != BuildModeEnum.PERF){
           // System.out.println("evaluating {"+expression+"}");
        }
        BinaryTree bt =  new BinaryTree(expression);
        return evaluateLocalVariable(bt);
    }
    public IScript evaluateLocalVariable(BinaryTree bt) throws HawkException{

        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        if(bt == null ||  multiLineScript == null){
            throw new HawkException("invalid");
        }
        IScript result = null;
        Expression infix = bt.getInfixExpression();
        if(infix == null || infix.getExpression() == null){
            System.out.println("invalid expression {"+bt.getInputExpression()+"}");
            logger.severe("invalid expression {"+bt.getInputExpression()+"}");
            throw new HawkException("invalid expression {"+bt.getInputExpression()+"}");
        }
        evaluateLocalVariableInternal(infix);
        bt.createTree();
        result = bt.calculate();

        return result;
    }

    private  void evaluateLocalVariableInternal(Expression infix) throws HawkException {
        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        List<Element> infixElement = infix.getExpression();
        IScript operandValueScript = null;
        AssignmentScript sas = null;
        int scriptCount = 0;
        MultiLineScript originalMLS = multiLineScript;
        for (int i=0;i<infixElement.size();i++) {
            Element element = infixElement.get(i);
            Element prevElement = i>0?infixElement.get(i-1):null;
            multiLineScript = originalMLS;
            if(multiLineScript == null){
                System.out.println("could not evaluate {"+element+"}");
                logger.severe("could not evaluate {"+element+"}");
                throw new HawkException("could not evaluate {"+element+"}");
            }
            if(element.isOperand()){
                if(
                        element.isOperand()&& element.isVariable()
                            &&
                        (
                            prevElement == null
                                    ?
                                            true
                                        :
                                            prevElement.isOperator()
                                                        ?
                                                                !OperatorEnum.value(prevElement.getElement()).equals(OperatorEnum.REFERENCE)
                                                            :
                                                                true
                        )
                  ){
                    String operand = element.toString();

                    Variable operandValue = null;

                    if(element.isExecFunction()){

                        ExecFunctionScript script = null;
                        Map<Integer,String> lineExecMatcherMap = PatternMatcher.match
                                                                                (
                                                                                    ExecFunctionScript.getPattern(),
                                                                                    operand
                                                                                );

                        script = ExecFunctionScript.createScript(lineExecMatcherMap);


                        if(script!= null){
                            script.setLineNumber(this.getLineNumber());
                            script.setOuterMultiLineScript(multiLineScript);
                        }

                        IScript rtnValue = script.execute();

                        operandValue = rtnValue.getVariableValue();
                        
                        LocalVarDeclScript lvds = new LocalVarDeclScript();
                        lvds.setLocalVar(rtnValue.getVariableValue());
                        lvds.setLocalVarValue(operandValue);
                        element.setValue(rtnValue);
                        
                    }else if(element.isExecModule()){

                        ExecModuleScript script = null;
                        Map<Integer,String> lineExecMatcherMap = PatternMatcher.match
                                                                                (
                                                                                    ExecModuleScript.getPattern(),
                                                                                    operand
                                                                                );

                        script = ExecModuleScript.createScript(lineExecMatcherMap);


                        if(script!= null){
                            script.setLineNumber(this.getLineNumber());
                            script.setOuterMultiLineScript(multiLineScript);
                        }

                        IScript rtnValue = script.execute();

                        operandValue = rtnValue.getVariableValue();
                        
                        LocalVarDeclScript lvds = new LocalVarDeclScript();
                        lvds.setLocalVar(rtnValue.getVariableValue());
                        lvds.setLocalVarValue(operandValue);
                        element.setValue(rtnValue);
                        

                    }else{
                        do {

                                operandValueScript = multiLineScript.getLocalValue(operand);
                                if(operandValueScript != null){
                                    operandValue = operandValueScript.getVariableValue();
                                }
                                if (operandValue == null) {
                                    if (multiLineScript.getFunctionScript() != null) {
                                        operandValueScript = multiLineScript.getFunctionScript()
                                                                            .getParamValue(operand);
                                        if(operandValueScript != null){
                                            operandValue = operandValueScript.getVariableValue();
                                        }

                                        if (operandValue == null) {

                                            operandValueScript = ScriptInterpreter.getInstance()
                                                                                  .getGlobalValue(operand);
                                            if(operandValueScript != null){
                                                operandValue = operandValueScript.getVariableValue();

                                            }

                                        }


                                    }
                                }
                                if(operandValue != null){
                                    operandValueScript.getVariable().setVariableValue(operandValue.getVariableValue());
                                    element.setValue(operandValueScript);
                                    
                                    break;
                               }
                            multiLineScript = multiLineScript.getOuterMultiLineScript();
                             if(multiLineScript == null){
                                System.out.println("could not evaluate {"+element+"}");
                                logger.severe("could not evaluate {"+element+"}");
                                throw new HawkException("could not evaluate {"+element+"}");
                             }
                        } while (true);
                    }

                           scriptCount = scriptCount +1;
                }else{
                     LocalVarDeclScript lvds = new LocalVarDeclScript();
                     Variable operandValue = new Variable(VarTypeEnum.VAR, null, element.getElement());
                     operandValue.setVariableValue
                                (
                                    element.isDouble()
                                    ?
                                            new DoubleDataType(Double.parseDouble(element.getElement()))
                                        :
                                            new StringDataType(element.getElement())
                                );
                     lvds.setLocalVarValue(operandValue);
                     lvds.setLocalVar(operandValue);
                     lvds.getVariable().setVariableValue(operandValue.getVariableValue());
                     element.setValue(lvds);
                }
            }
        }

       
    }

    public IScript add(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript and(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript divide(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript greaterThan(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript greaterThanEqualTo(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript lessThan(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript lessThanEqualTo(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript modulus(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript multiply(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript or(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript equalTo(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript subtract(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript refer(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript assign(IScript dataType) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IScript arrayBracket(IScript otherScript) throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public abstract String toUI();

    public  Map<Object,Object> toJavaMap() throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object toJava() throws HawkException{
         return ""+this.getVariableValue();
    }

    public int length() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}




