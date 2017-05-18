package org.commons.ds.tree.bintreeng;

/**
 *
 * @author manosahu
 */
public class TopBinTreeViewImpl<K> extends AbstractVerticalView<K> {

    @Override
    protected boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord) {
        return firstCord.isVerticallyAbove(secondCord);
    }

}
