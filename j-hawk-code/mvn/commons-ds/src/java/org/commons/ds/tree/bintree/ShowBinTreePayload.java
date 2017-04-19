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


package org.commons.ds.tree.bintree;

import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
public class ShowBinTreePayload implements IBinTreePayload{

    private int length;

    
    
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

   
    

   
    

    @Override
    public IBinTreePayload onVisit(IBinTreeNode treeNode) {
        int i = 0;

        while (i < this.getLength()) {
            System.out.print("  ");
            i++;
        }
        System.out.println(this.toString());
        
        return this;

    }

    
    @Override
    public IBinTreePayload beforeLeftVisit(IBinTreeNode treeNode) {
        ShowBinTreePayload leftBinTreePayload = new ShowBinTreePayload();
        leftBinTreePayload.setLength(this.getLength()+1);
        return leftBinTreePayload;
    }

    
    @Override
    public IBinTreePayload beforeRightVisit(IBinTreeNode treeNode) {
        ShowBinTreePayload rightBinTreePayload = new ShowBinTreePayload();
        rightBinTreePayload.setLength(this.getLength()+1);
        return rightBinTreePayload;
    }

    @Override
    public boolean isCalculated() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(IObject script) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
