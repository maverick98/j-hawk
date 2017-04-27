/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

import java.util.Map;

/**
 *
 * @author manosahu
 * @param <IPayload>
 */
public class MatrixIterator<IPayload> extends AbstractMatrixIterator<IPayload> {

    @Override
    public void iterate() {
        IMatrixNode matrixNode = this.getMatrixNode();
        this.iterateInternal(matrixNode);
    }

    private void iterateInternal(IMatrixNode node) {

        if (node.isLeaf()) {
            IMatrixPayload payload = (IMatrixPayload) node.getPayload();
            payload.onVisit(node);
        } else {
            for (Map.Entry<Integer, IMatrixNode> entry : node.getChildren().entrySet()) {
                this.iterateInternal(entry.getValue());
            }
        }
    }
}
