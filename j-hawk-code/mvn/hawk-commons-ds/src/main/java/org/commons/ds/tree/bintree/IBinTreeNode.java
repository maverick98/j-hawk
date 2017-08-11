/*
 * This file is part of j-hawk
 * Copyright (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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

import org.commons.ds.INode;
import org.commons.ds.exp.IObject;

/**
 *
 * @author manosahu
 */
/**
 * This represents the node of the binary tree.
 */
public interface IBinTreeNode extends INode {

    public void setLeft(IBinTreeNode left);

    public void setRight(IBinTreeNode right);

    public boolean isCalculated();

    public IBinTreeNode getLeft();

    public IBinTreeNode getRight();

    public void setValue(IObject script);

    public IObject getValue();

  
}
