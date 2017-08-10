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
package org.hawk.lang;

import java.util.List;
import java.util.Map;
import org.common.di.AppContainer;
import org.commons.ds.exp.InfixExpression;
import org.commons.ds.element.IElement;
import org.commons.ds.exp.IExpressionService;
import org.commons.ds.operator.IOperator;
import org.commons.ds.operator.OperatorFactory;
import org.commons.ds.element.ElementBinaryTree;
import org.commons.ds.element.MatrixElement;
import org.hawk.ds.element.ExecFunctionElement;
import org.hawk.ds.element.ExecModuleElement;
import org.hawk.ds.exp.HawkExpressionServiceImpl;
import org.hawk.ds.operator.ReferenceOperator;


import org.hawk.executor.cache.multiline.function.FunctionScriptCache;
import org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache;
import org.hawk.executor.cache.singleline.globalvar.IGlobalVariableScriptCache;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.function.ExecFunctionScript;
import org.hawk.lang.function.ExecModuleScript;
import org.hawk.lang.function.FunctionScript;
import org.hawk.lang.grammar.Grammar;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.object.VariableDeclScript;
import org.hawk.lang.object.matrix.MatrixScript;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.DataTypeFactory;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.hawk.xml.XMLUtil;

/**
 * Abstract class for IScript. This provides default implementation for
 * {@link IScript#pushLocalVars} and {@link IScript#popLocalVars}
 *
 * @version 1.0 29 Apr, 2010
 * @author msahu
 */
public abstract class AbstractScript implements IScript {

    private static final HawkLogger logger = HawkLogger.getLogger(AbstractScript.class.getName());

    private static Grammar grammar;

    protected MultiLineScript outerMultiLineScript = null;

    private int lineNumber;

    private boolean lastScript = false;
    
    private IExpressionService expService  =  new HawkExpressionServiceImpl();

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

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public static Grammar getGrammar() {
        if (grammar == null) {
            try {
                grammar = XMLUtil.unmarshal(Thread.currentThread().getContextClassLoader().getResourceAsStream("language/grammar.xml"), Grammar.class);
            } catch (Exception ex) {
                logger.error(ex);
          //      throw new HawkError(ex);
            }

        }
        return grammar;
    }

    @Override
    public abstract IScript execute() throws Exception;

    protected abstract MultiLineScript findNearestOuterMLScript();

    protected FunctionScript findEncapsulatingFunctionScript() {
        FunctionScript result = null;
        MultiLineScript mls = this.getOuterMultiLineScript();
        do {
            if (mls != null && mls.getFunctionScript() != null) {
                result = mls.getFunctionScript();
                break;
            }
            if (mls != null) {
                mls = mls.getOuterMultiLineScript();
            }else{
                break;
            }
        } while (true);

        return result;
    }

    public boolean evaluateLocalBoolean(String expression) throws Exception {
        MultiLineScript multiLineScript = this.findNearestOuterMLScript();

        if (expression == null || expression.isEmpty() || multiLineScript == null) {
            throw new Exception("invalid");
        }
        Variable rtn = ((IObjectScript) (this.evaluateLocalVariable(expression))).getVariableValue();
        BooleanDataType result = (BooleanDataType) rtn.getValue();
        return result.isData();
    }

    public IObjectScript evaluateGlobalVariable(String expression) throws Exception {
        if (expression == null || expression.isEmpty()) {
            throw new Exception("Invalid");
        }
        IObjectScript rtn;
        try {
            ElementBinaryTree bt = new ElementBinaryTree(expression , expService);
            InfixExpression infix = bt.getInfixExpression();
            evaluateGlobalVariableInternal(infix);
            bt.createTree();
            rtn = (IObjectScript) bt.calculate();
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return rtn;
    }

    private void evaluateGlobalVariableInternal(InfixExpression infix) throws Exception {

        List<IElement> infixElement = infix.getExpression();
        IGlobalVariableScriptCache scriptInterpreter = AppContainer.getInstance().getBean(GlobalVariableScriptCache.class);
        for (IElement element : infixElement) {
          //  if (element.isOperand()) {
                if (element.shouldEvaluate()) {
                    String operand = element.toString();
                    //Fix me struct can be global var
                    Variable operandVar = new Variable(VarTypeEnum.VAR, null, operand);
                    IObjectScript operandVarScript = null;
                    Variable operandValue = null;

                    if (scriptInterpreter.isGlobalVarDeclared(operandVar)) {
                        operandVarScript = scriptInterpreter.getGlobalValue(operand);
                        operandValue = operandVarScript.getVariableValue();
                    }
                    if (operandValue == null) {
                        throw new Exception("undeclared variable {" + operand + "}");
                    } else {
                        //element.setElement(operandValue + "");
                        element.setValue(operandVarScript);
                        break;
                    }
                } else {
                    VariableDeclScript lvds = new VariableDeclScript();
                    Variable operandValue = new Variable(VarTypeEnum.VAR, null, element.getElement());
                    operandValue.setValue(
                            DataTypeFactory.createDataType(element.getElement())
                    );
                    lvds.setVariable(operandValue);
                    lvds.setVariableValue(operandValue);
                    element.setValue(lvds);
                }

            //}
        }

    }

    public IObjectScript evaluateLocalVariable(String expression) throws Exception {

        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        if (expression == null || expression.isEmpty() || multiLineScript == null) {
            throw new Exception("invalid");
        }
        if(expression.equals("arr[](i) <=(  pivot)")){
            System.out.println("prblem");
        }
        ElementBinaryTree bt;
        try {
            bt = new ElementBinaryTree(expression,expService);
        } catch (Exception ex) {
            throw new Exception(ex);
        }

        IObjectScript rtn = evaluateLocalVariable(bt);

        return rtn;
    }

    public IObjectScript evaluateLocalVariable(ElementBinaryTree bt) throws Exception {

        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        if (bt == null || multiLineScript == null) {
            throw new IllegalArgumentException("invalid");
        }
        IObjectScript result;
        InfixExpression infix = bt.getInfixExpression();
        if (infix == null || infix.getExpression() == null) {
            //System.out.println("invalid expression {"+bt.getInputExpression()+"}");
            logger.error("invalid expression {" + bt.getInputExpression() + "}");
            throw new Exception("invalid expression {" + bt.getInputExpression() + "}");
        }
        if(infix.getEquation().equals("rtnValue =( {exec b()})")){
                  System.out.println("prblem area");
        }
        evaluateLocalVariableInternal(infix);
        try {
            bt.createTree();
            result = (IObjectScript) bt.calculate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }

        return result;
    }

    private boolean isNotReferenceOperator(String data) {
        boolean notReferenceOperator;

        IOperator prevOperator = OperatorFactory.getInstance().findBySymbol(data);
        if (prevOperator != null) {
            notReferenceOperator = !(prevOperator instanceof ReferenceOperator);
        } else {
            notReferenceOperator = true;
        }
        return notReferenceOperator;

    }

    private void evaluateLocalVariableInternal(InfixExpression infix) throws Exception {
        MultiLineScript multiLineScript = this.findNearestOuterMLScript();
        List<IElement> infixElement = infix.getExpression();
        IObjectScript operandValueScript;

        MultiLineScript originalMLS = multiLineScript;
        for (int i = 0; i < infixElement.size(); i++) {
            IElement element = infixElement.get(i);
            String curData = element.getElement();

            IElement prevElement = i > 0 ? infixElement.get(i - 1) : null;

            boolean prevOprNotReference;
            if (prevElement != null) {
                String prevData = prevElement.getElement();
                prevOprNotReference = this.isNotReferenceOperator(prevData);
            } else {
                prevOprNotReference = true;
            }
            multiLineScript = originalMLS;
            if (multiLineScript == null) {
                System.out.println("could not evaluate {" + element + "}");
                logger.error("could not evaluate {" + element + "}");
                throw new Exception("could not evaluate {" + element + "}");
            }
            
                if ( prevOprNotReference &&element.shouldEvaluate()) {
                    String operand = element.toString();

                    Variable operandValue = null;

                    if (element instanceof  ExecFunctionElement) {

                        ExecFunctionScript script;
                        Map<Integer, String> lineExecMatcherMap = PatternMatcher.match(
                                new ExecFunctionScript().getPatterns(),
                                operand
                        );

                        script = (ExecFunctionScript) new ExecFunctionScript().createScript(lineExecMatcherMap);

                        IObjectScript rtnValue = null;
                        if (script != null) {
                            script.setLineNumber(this.getLineNumber());
                            script.setOuterMultiLineScript(multiLineScript);
                            rtnValue = (IObjectScript) script.execute();
                        }

                        if (rtnValue != null) {
                            operandValue = rtnValue.getVariableValue();

                            LocalVarDeclScript lvds = new LocalVarDeclScript();
                            lvds.setLocalVar(rtnValue.getVariableValue());
                            lvds.setLocalVarValue(operandValue);
                            element.setValue(rtnValue);
                        }

                    } else if (element instanceof ExecModuleElement) {

                        ExecModuleScript script;
                        Map<Integer, String> lineExecMatcherMap = PatternMatcher.match(
                                new ExecModuleScript().getPatterns(),
                                operand
                        );

                        script = (ExecModuleScript) new ExecModuleScript().createScript(lineExecMatcherMap);

                        IObjectScript rtnValue = null;
                        if (script != null) {
                            script.setLineNumber(this.getLineNumber());
                            script.setOuterMultiLineScript(multiLineScript);
                            rtnValue = (IObjectScript) script.execute();
                        }
                        if (rtnValue != null) {
                            operandValue = rtnValue.getVariableValue();
                            element.setValue(rtnValue);
                        }

                    }else if (element instanceof MatrixElement) {
                        MatrixScript matrixScript;
                        matrixScript = MatrixScript.createScript(operand, "", true);
                        IObjectScript rtnValue = null;
                        if(matrixScript!= null){
                            matrixScript.setLineNumber(this.getLineNumber());
                            matrixScript.setOuterMultiLineScript(multiLineScript);
                            rtnValue = matrixScript.execute();
                        }
                        if (rtnValue != null) {
                            operandValue = rtnValue.getVariableValue();
                            element.setValue(rtnValue);
                        }
                    }else {
                        do {
                        
                            operandValueScript = (IObjectScript) multiLineScript.getLocalValue(operand);
                            if (operandValueScript != null) {
                                operandValue = operandValueScript.getVariableValue();
                            }
                            if (operandValue == null) {
                                if (multiLineScript.getFunctionScript() != null) {
                                    operandValueScript = (IObjectScript) multiLineScript.getFunctionScript()
                                            .getParamValue(operand);
                                    if (operandValueScript != null) {
                                        operandValue = operandValueScript.getVariableValue();
                                    }

                                    if (operandValue == null) {

                                        operandValueScript = AppContainer.getInstance().getBean(GlobalVariableScriptCache.class)
                                                .getGlobalValue(operand);
                                        if (operandValueScript != null) {
                                            operandValue = operandValueScript.getVariableValue();

                                        }

                                    }

                                    if (operandValue == null) {

                                        MultiLineScript outerMLS = this.getOuterMultiLineScript();
                                        List<FunctionScript> list = null;
                                        do {

                                            if (outerMLS.getRootFunctionNode() != null) {
                                                list = outerMLS.getRootFunctionNode().findAllOverloadedFunctionScripts(operand);
                                            }
                                            if (list != null) {
                                                break;
                                            }
                                            outerMLS = outerMLS.getOuterMultiLineScript();
                                        } while (outerMLS != null);

                                        if (list != null && !list.isEmpty()) {
                                            operandValueScript = list.get(0);
                                        }

                                        if (operandValueScript != null) {
                                            operandValue = operandValueScript.getVariableValue();

                                        }

                                    }

                                    if (operandValue == null) {
                                        List<FunctionScript> list = AppContainer.getInstance().getBean(FunctionScriptCache.class)
                                                .findAllOverloadedFunctionScripts(operand);

                                        if (list != null && !list.isEmpty()) {
                                            operandValueScript = list.get(0);
                                        }

                                        if (operandValueScript != null) {
                                            operandValue = operandValueScript.getVariableValue();

                                        }
                                    }

                                }
                            }
                            if (operandValue != null) {
                                operandValueScript.getVariable().setValue(operandValue.getValue());
                                operandValueScript.getVariableValue().setValue(operandValue.getValue());
                                element.setValue(operandValueScript);

                                break;
                            }
                            multiLineScript = multiLineScript.getOuterMultiLineScript();
                            if (multiLineScript == null) {

                                LocalVarDeclScript lvds1 = new LocalVarDeclScript();
                                Variable operandValue1 = new Variable(VarTypeEnum.VAR, null, curData);
                                operandValue1.setValue(
                                        DataTypeFactory.createDataType(curData)
                                );
                                lvds1.setLocalVarValue(operandValue1);
                                lvds1.setLocalVar(operandValue1);
                                lvds1.getVariable().setValue(operandValue1.getValue());
                                element.setValue(lvds1);
                                break;
                            }
                        } while (true);
                    }

                } else {
                    LocalVarDeclScript lvds = new LocalVarDeclScript();
                    Variable operandValue = new Variable(VarTypeEnum.VAR, null, curData);
                    operandValue.setValue(
                            DataTypeFactory.createDataType(curData)
                    );
                    lvds.setLocalVarValue(operandValue);
                    lvds.setLocalVar(operandValue);
                    lvds.getVariable().setValue(operandValue.getValue());
                    element.setValue(lvds);
                }
            
        }

    }

    @Override
    public Map<Object, Object> toJavaMap() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
