package org.commons.ds.tree.bintreeng;

/**
 *
 * @author manosahu
 */
public class DownBinTreeViewImpl<K> extends AbstractVerticalView<K> {

    @Override
    protected boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord) {
        return firstCord.isVerticallyBelow(secondCord);
    }

}
