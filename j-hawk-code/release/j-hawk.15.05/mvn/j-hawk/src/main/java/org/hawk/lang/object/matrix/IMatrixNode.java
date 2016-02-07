/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

import java.util.Map;
import org.commons.ds.INode;

/**
 *
 * @author manosahu
 */
public interface IMatrixNode extends INode {

    public String show();

    public boolean isLeaf();

    public boolean hasChildren();

    public Integer getChildrenCount();

    public Integer getNextChildrenIndex();

    public boolean addChild(IMatrixNode matrixNode);

    public boolean removeChildAt(Integer index);

    public IMatrixNode getAt(Integer index);
    
    public boolean setAt(Integer index , IMatrixNode matrixNode);

    public IMatrixNode get(Integer row, Integer column);

    public boolean set(Integer row, Integer column, IMatrixNode matrixNode);

    public String getSymbol();

    public void setSymbol(String symbol);

    public Integer getColumnCount();

    public Integer getRowCount();

    public boolean canAdd(IMatrixNode otherMatrixNode);

    public IMatrixNode add(IMatrixNode otherMatrixNode);

    public Map<Integer, IMatrixNode> getChildren();

    public void setChildren(Map<Integer, IMatrixNode> children);

}
