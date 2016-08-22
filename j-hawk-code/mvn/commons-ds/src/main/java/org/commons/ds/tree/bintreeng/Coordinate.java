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

    public final Coordinate getLeft() {
        Integer xleft = this.getX() - 1;
        Integer yleft = this.getY() + 1;
        Coordinate leftCord = new Coordinate(xleft, yleft);
        return leftCord;
    }

    public final Coordinate getRight() {
        Integer xleft = this.getX() + 1;
        Integer yleft = this.getY() + 1;
        Coordinate leftCord = new Coordinate(xleft, yleft);
        return leftCord;
    }

    public boolean isVerticallyAbove(final Coordinate herStuff){
        boolean rtn;
        Coordinate myStuff = this;
        if(myStuff.getX().equals(herStuff.getX())){
            
            //Is woman on top ? ok dont fantasize too much .. go back to code
            rtn = myStuff.getY() <  herStuff.getY();
            
        }else{
            rtn = false;
        }
        
        return rtn;
    }
    @Override
    public final String toString() {
        return "Coordinate{" + "x=" + x + ", y=" + y + '}';
    }

}
