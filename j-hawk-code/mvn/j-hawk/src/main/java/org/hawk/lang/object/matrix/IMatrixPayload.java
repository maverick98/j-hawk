/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

import org.commons.ds.IPayload;
import org.hawk.lang.object.IObjectScript;

/**
 *
 * @author manosahu
 */
public interface IMatrixPayload extends IPayload {
    
    public IMatrixPayload copy();

    public IMatrixPayload onVisit(IMatrixNode treeNode);

     public String getExpression();

    public void setExpression(String expression);
}
