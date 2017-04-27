/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

/**
 *
 * @author manosahu
 * @param <IPayload>
 */
public abstract class AbstractMatrixIterator<IPayload> implements IMatrixIterator<IPayload> {

    IMatrixNode matrixNode;

    @Override
    public IMatrixNode getMatrixNode() {
        return this.matrixNode;
    }

    public void setMatrixNode(IMatrixNode matrixNode) {
        this.matrixNode = matrixNode;
    }
    

    @Override
    public abstract void iterate();

}
