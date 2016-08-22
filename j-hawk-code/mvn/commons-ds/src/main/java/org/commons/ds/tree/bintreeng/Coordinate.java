package org.commons.ds.tree.bintreeng;

import org.tukaani.xz.CorruptedInputException;

/**
 *
 * @author manosahu
 */
public final class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate(final Integer x, final Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    /**
     * This is NOT left mathematical sense Make sure you understand this.
     * Otherwise use it at your own risk
     *
     * @return
     */
    public final Coordinate getLeft() {
        Integer xleft = this.getX() - 1;
        Integer yleft = this.getY() + 1;
        Coordinate leftCord = new Coordinate(xleft, yleft);
        return leftCord;
    }

    /**
     * This is NOT right mathematical sense Make sure you understand this.
     * Otherwise use it at your own risk
     *
     * @return
     */
    public final Coordinate getRight() {
        Integer xleft = this.getX() + 1;
        Integer yleft = this.getY() + 1;
        Coordinate leftCord = new Coordinate(xleft, yleft);
        return leftCord;
    }

    public boolean isVerticallyAbove(final Coordinate herStuff) {
        if (herStuff == null) {
            return false;
        }
        boolean rtn;
        Coordinate myStuff = this;
        if (myStuff.getX().equals(herStuff.getX())) {

            //Is woman on top ? ok dont fantasize too much .. go back to code
            rtn = myStuff.getY() < herStuff.getY();

        } else {
            rtn = false;
        }

        return rtn;
    }

    public boolean isVerticallyBelow(final Coordinate herStuff) {
        if (herStuff == null) {
            return false;
        }
        return herStuff.isVerticallyAbove(this);
    }

    public boolean isLeft(final Coordinate herStuff) {
        if (herStuff == null) {
            return false;
        }

        boolean rtn;
        Coordinate myStuff = this;
        if (myStuff.getY().equals(herStuff.getY())) {

            // Cant imagine anything lewd here... Sorry guys 
            rtn = myStuff.getX() < herStuff.getX();

        } else {
            rtn = false;
        }

        return rtn;

    }

    public boolean isRight(final Coordinate herStuff) {
        if (herStuff == null) {
            return false;
        }
        return herStuff.isLeft(this);
    }

    @Override
    public final String toString() {
        return "Coordinate{" + "x=" + x + ", y=" + y + '}';
    }

}
