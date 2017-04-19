package org.commons.ds.tree.bintreeng;

/**
 *
 * @author manosahu
 */
public class LeftSideBinTreeViewImpl<K> extends AbstractHorizontalView<K> {

    @Override
    protected boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord) {
        return firstCord.isLeft(secondCord);
    }

}
