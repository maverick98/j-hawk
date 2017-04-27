/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.element;

import org.commons.ds.exp.IObject;
import org.commons.ds.tree.bintree.IBinTreeNode;
import org.commons.ds.tree.bintree.IBinTreePayload;
import org.commons.ds.tree.bintree.ShowBinTreePayload;

/**
 *
 * @author manosahu
 */
public class ElementBinTreePayload implements IBinTreePayload {

    private IElement data;
    
    
    private int length;

    public ElementBinTreePayload(IElement data) {
        this.data = data;
    }

    private ElementBinTreePayload() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public IElement getData() {
        return data;
    }

    public void setData(IElement data) {
        this.data = data;
    }

    @Override
    public IBinTreePayload onVisit(IBinTreeNode treeNode) {
          int i = 0;

        while (i < this.getLength()) {
            System.out.print("  ");
            i++;
        }
       // System.out.println(this.toString());
        
        return this;
    }

    @Override
    public IBinTreePayload beforeLeftVisit(IBinTreeNode treeNode) {
        ElementBinTreePayload leftBinTreePayload = new ElementBinTreePayload();
        leftBinTreePayload.setLength(this.getLength()+1);
        return leftBinTreePayload;
    }

    
    @Override
    public IBinTreePayload beforeRightVisit(IBinTreeNode treeNode) {
        ElementBinTreePayload rightBinTreePayload = new ElementBinTreePayload();
        rightBinTreePayload.setLength(this.getLength()+1);
        return rightBinTreePayload;
    }

    @Override
    public boolean isCalculated() {
        return this.getData().getValue() != null;
    }

    public void setValue(IObject script) {

        this.getData().setValue(script);
    }

    public IObject getValue() {

        return this.data.getValue();
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

   
}
