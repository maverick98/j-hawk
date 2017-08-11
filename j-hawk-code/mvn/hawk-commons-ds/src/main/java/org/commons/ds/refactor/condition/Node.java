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
package org.commons.ds.refactor.condition;

import java.util.StringTokenizer;
import org.commons.ds.element.IElement;

/**
 *
 * @author manosahu
 */
public class Node {

    private IElement element;
    private StringTokenizer tokenizer = null;
    private static final String DELIM = ".";

    private Node() {
    }

    public static Node createElementNode(IElement element) {
        Node elementNode = new Node();
        elementNode.setElement(element);

        return elementNode;
    }

    public IElement getElement() {
        return element;
    }

    public void setElement(IElement element) {
        this.element = element;
    }

    public String getSequence() {
        return element.getHorizontalParserSequence();
    }

    public boolean isAdd() {
        return element.shouldAdd();
    }

    @Override
    public String toString() {
        return "Node{" + "element=" + element + ", sequence=" + element.getHorizontalParserSequence() + ", add=" + element.shouldAdd() + '}';
    }

    public boolean hasMoreTokens() {
        if (tokenizer == null) {
            tokenizer = new StringTokenizer(this.getSequence(), DELIM);
        }
        return tokenizer.hasMoreTokens();
    }

    public String nextToken() {
        if (tokenizer == null) {
            tokenizer = new StringTokenizer(this.getSequence(), DELIM);
        }
        String result = null;
        if (tokenizer.hasMoreTokens()) {
            result = tokenizer.nextToken();
        } else {
            result = null;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.element.getHorizontalParserSequence() != null ? this.element.getHorizontalParserSequence().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if ((this.element.getHorizontalParserSequence() == null) ? (other.element.getHorizontalParserSequence() != null) : !this.element.getHorizontalParserSequence().equals(other.getElement().getHorizontalParserSequence())) {
            return false;
        }
        return true;
    }
}
