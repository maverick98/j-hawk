

package org.commons.ds.tree.bintreeng;

import java.util.List;

/**
 *
 * @author manosahu
 */
public interface IBinTreeView<K> {

    public List<Node<K>> showView(final Node<K> rootNode);
}
