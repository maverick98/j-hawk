/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.object.matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.commons.ds.IPayload;
import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public class MatrixNode implements IMatrixNode {

    private String symbol;

    private Map<Integer, IMatrixNode> children = new HashMap<>();

    private IMatrixPayload payload;

    @Override
    public IMatrixPayload getPayload() {
        return payload;
    }

    @Override
    public void setPayload(IPayload payload) {
        this.payload = (IMatrixPayload) payload;
    }

    @Override
    public String toString() {
        return this.getSymbol() + " & " + this.getPayload().getExpression();
    }

    @Override
    public boolean isLeaf() {
        return this.getChildren().isEmpty();
    }

    @Override
    public boolean hasChildren() {
        return !this.isLeaf();
    }

    @Override
    public Integer getChildrenCount() {
        return this.getChildren().size();
    }

    @Override
    public Integer getNextChildrenIndex() {
        return this.getChildrenCount();
    }

    @Override
    public boolean addChild(IMatrixNode matrixNode) {

        this.getChildren().put(this.getNextChildrenIndex(), matrixNode);
        return true;
    }

    @Override
    public boolean removeChildAt(Integer index) {

        this.getChildren().remove(index);
        return true;
    }

    @Override
    public IMatrixNode getAt(Integer index) {
        IMatrixNode rtn = null;
        if (!this.isLeaf()) {
            rtn = this.getChildren().get(index);
        } else {
            rtn = this;
        }
        return rtn;
    }

    @Override
    public boolean setAt(Integer index, IMatrixNode matrixNode) {
        boolean rtn = false;

        this.getChildren().put(index, matrixNode);
        rtn = true;

        return rtn;
    }

    @Override
    public Integer getColumnCount() {
        Integer columnCount = -1;
        for (Entry<Integer, IMatrixNode> entry : this.getChildren().entrySet()) {
            Integer c = entry.getValue().getRowCount();
            if (c == 0) {
                c = 1;
            }
            if (columnCount != -1) {
                if (!columnCount.equals(c)) {
                    throw new Error("column size has to be same to be called as a matrix");
                }
            } else {
                columnCount = c;
            }
        }
        return columnCount;
    }

    @Override
    public Integer getRowCount() {
        return this.getChildren().size();
    }

    @Override
    public boolean canAdd(IMatrixNode otherMatrixNode) {
        boolean result = false;
        if (this.getRowCount().equals(otherMatrixNode.getRowCount())) {
            if (this.getColumnCount().equals(otherMatrixNode.getColumnCount())) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public IMatrixNode add(IMatrixNode otherMatrixNode) {
        if (!this.canAdd(otherMatrixNode)) {
            throw new Error("cant add");
        }
        IMatrixNode matrixNode = new MatrixNode();
        int row = this.getRowCount();
        int column = this.getColumnCount();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                IMatrixNode thisNode = this.get(i, j);
                IMatrixNode thatNode = otherMatrixNode.get(i, j);
                IMatrixNode resultNode = new MatrixNode();
                if (thisNode.isLeaf() && thatNode.isLeaf()) {
                    try {
                        IObject rtnObject = thisNode.getPayload().getValue().add(thatNode.getPayload().getValue());
                        IMatrixPayload payload = ((IMatrixPayload) thisNode.getPayload()).copy();
                        payload.setValue(rtnObject);
                        resultNode.setPayload(payload);
                    } catch (Exception ex) {
                        Logger.getLogger(MatrixNode.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if (!thisNode.isLeaf() && !thatNode.isLeaf()) {
                    resultNode = thisNode.add(thatNode);
                }

                matrixNode.set(i, j, resultNode);
            }
        }

        return matrixNode;

    }

    @Override
    public boolean set(Integer row, Integer column, IMatrixNode matrixNode) {
        IMatrixNode result;
        IMatrixNode rowObject = this.getAt(row);
        if (rowObject == null) {
            rowObject = new MatrixNode();
        }
        this.addChild(rowObject);
        rowObject.setAt(column, matrixNode);
        return true;
    }

    @Override
    public IMatrixNode get(Integer row, Integer column) {

        IMatrixNode result;
        IMatrixNode rowObject = this.getAt(row);
        if (rowObject == null) {
            return null;
        }
        IMatrixNode rowVector = rowObject;
        result = rowVector.getAt(column);
        return result;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Map<Integer, IMatrixNode> getChildren() {
        return children;
    }

    @Override
    public void setChildren(Map<Integer, IMatrixNode> children) {
        this.children = children;
    }

    public String show() {
        int space = 0;
        return this.showInternal(this);
    }

    private String showInternal(IMatrixNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append(" ");
        if (!node.isLeaf()) {
            for (Entry<Integer, IMatrixNode> entry : node.getChildren().entrySet()) {

                if (entry.getValue().isLeaf()) {
                    sb.append(" ");
                    sb.append(entry.getValue().getPayload().getValue().toString());
                } else {
                    sb.append(this.showInternal(entry.getValue()));
                }
            }
        }else{
            sb.append(node.getPayload().getValue().toUI());
        }
        sb.append(" ");
        sb.append("|");
        sb.append(",");

        return sb.toString();
    }

}
