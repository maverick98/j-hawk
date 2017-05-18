/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

import org.commons.ds.element.IElement;
import org.commons.ds.element.NumberElement;
import org.commons.string.StringUtil;
import org.hawk.ds.element.ExecElement;
import org.hawk.ds.element.VariableElement;
import org.hawk.lang.object.matrix.MatrixScript.MatrixPayload;

/**
 *
 * @author manosahu
 */
public class MatrixParser {

    /**
     * [`exec createMatrix(a,d)`,[[a,13,[1]]],d]
     *
     * @param input
     * @param matrixScript
     * @return
     * @throws java.lang.Exception
     */
    public MatrixNode parseMatrix(String input , MatrixScript matrixScript) throws Exception {
        MatrixNode matrixNode = null;
        if (StringUtil.isNullOrEmpty(input)) {
            matrixNode = null;
        } else {
            matrixNode = new MatrixNode();
            MatrixPayload matrixPayload = matrixScript.new MatrixPayload();
            
            matrixNode.setPayload(matrixPayload);
            matrixNode.setSymbol("[]");
            String exp = StringUtil.parseDelimeterData(input, '[', ']');
            if(!StringUtil.isNullOrEmpty(exp)){
                matrixPayload.setExpression(exp);
                parseMatrixInternal(exp, 0, matrixNode,matrixScript);
            }else{
                matrixPayload.setExpression(input);
            }
        }
        return matrixNode;
    }

    private void parseMatrixInternal(String input, int pos, MatrixNode curMatrixNode,MatrixScript matrixScript) throws Exception {

        input = input.replaceAll("\\)\\s*`", ")}");
        input = input.replaceAll("`", "{");
        for (int i = pos; i < input.length();) {

            
            String curInput = input.substring(i);
            String data = ExecElement.parseExec(curInput);
            if (data != null) {
                i += data.length();
               MatrixNode childMatrixNode = new MatrixNode();
                MatrixPayload matrixPayload = matrixScript.new MatrixPayload();
                childMatrixNode.setPayload(matrixPayload);
                matrixPayload.setExpression("`"+data+"`");
                //curMatrixNode.setSymbol("[]");
                curMatrixNode.addChild(childMatrixNode);
            } else {
                VariableElement variableElement = new VariableElement();
                IElement element = variableElement.parse(curInput, i);
                if (element != null) {
                    i += element.getShiftLength();
                    MatrixNode childMatrixNode = new MatrixNode();
                    MatrixPayload matrixPayload = matrixScript.new MatrixPayload();
                    childMatrixNode.setPayload(matrixPayload);
                    matrixPayload.setExpression(element.getElement());
                    // curMatrixNode.setSymbol("[]");
                    curMatrixNode.addChild(childMatrixNode);
                } else {
                    NumberElement numberElement = new NumberElement();
                    IElement element1 = numberElement.parse(curInput, i);
                    if (element1 != null) {
                        i += element1.getShiftLength();
                        MatrixNode childMatrixNode = new MatrixNode();
                       MatrixPayload matrixPayload = matrixScript.new MatrixPayload();
                        childMatrixNode.setPayload(matrixPayload);
                        matrixPayload.setExpression(element1.getElement());
                        //curMatrixNode.setSymbol("[]");
                        curMatrixNode.addChild(childMatrixNode);
                    } else {
                        if (curInput.charAt(0) == '[') {
                            String matrixAgain = StringUtil.parseDelimeterData(curInput, '[', ']');
                            if (!StringUtil.isNullOrEmpty(matrixAgain)) {
                                i += matrixAgain.length() + 1;
                                MatrixNode childMatrixNode = new MatrixNode();
                                MatrixPayload matrixPayload = matrixScript.new MatrixPayload();
                                childMatrixNode.setPayload(matrixPayload);
                                matrixPayload.setExpression(matrixAgain);
                                childMatrixNode.setSymbol("[]");

                                curMatrixNode.addChild(childMatrixNode);
                                parseMatrixInternal(matrixAgain, 0, childMatrixNode,matrixScript);
                            } else {
                                i++;
                            }
                        } else {
                            i++;
                        }
                    }
                }
            }

        }

    }
}
