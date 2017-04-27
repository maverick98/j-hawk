/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

/**
 *
 * @author manosahu
 */
public interface IMatrixIterator<IPayload> {

    public IMatrixNode getMatrixNode();

    public void iterate();
}
