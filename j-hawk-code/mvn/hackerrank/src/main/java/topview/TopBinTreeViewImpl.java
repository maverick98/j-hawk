package topview;









/**
 *
 * @author manosahu
 */
interface IBinTreeView {

    public java.util.List<Node> showView(final Node rootNode);
}

abstract class AbstractBinTreeView implements IBinTreeView {

    protected java.util.Map<Integer,Coordinate> nodeCordMap = new java.util.HashMap<>();
    
    @Override
    public abstract java.util.List<Node> showView(final Node rootNode);

    protected java.util.Map<Integer, Node> createMap(final Node node) {
        java.util.Map<Integer, Node> map = new java.util.LinkedHashMap<>();
        Coordinate rootCord = new Coordinate(0, 0);
        preOrder(node, rootCord, map);

        return map;
    }

    private void preOrder(final Node node, final Coordinate cord, final java.util.Map<Integer, Node> map) {

        if (node == null) {
            return;
        }
        //node.setCoordinate(cord);
        nodeCordMap.put(node.data,cord);
        this.storeInMap(map, node);
        preOrder(node.left, cord.getLeft(), map);
        preOrder(node.right, cord.getRight(), map);

    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }

    protected boolean storeInMap(final java.util.Map<Integer, Node> map, final Node node) {
        boolean rtn;
        if (map == null || node == null) {
            return false;
        }
        Node curNode = map.get(this.getKeyCordinateBasis(node));
        if (curNode != null) {
            if (this.compareCoordinate(nodeCordMap.get(node.data), nodeCordMap.get(curNode.data))) {
                map.remove(this.getKeyCordinateBasis(node));
                map.put(this.getKeyCordinateBasis(node), node);
                rtn = true;
            } else {
                rtn = false;
            }
        } else {
            map.put(this.getKeyCordinateBasis(node), node);
            rtn = true;
        }

        return rtn;
    }

    protected abstract Integer getKeyCordinateBasis(final Node node);

    protected abstract boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord);

}

class Node {

     int data;
     Node left;
     Node right;

     Coordinate coordinate;

    

}

final class Coordinate {

    private final Integer x;
    private final Integer y;

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

abstract class AbstractVerticalView  extends AbstractBinTreeView  {

    @Override
    public java.util.List<Node> showView(final Node rootNode) {
        java.util.List<Node> views = new java.util.ArrayList<>();
        java.util.Map<Integer, Node> map = this.createMap(rootNode);
        for (java.util.Map.Entry<Integer, Node> entry : map.entrySet()) {
            views.add(entry.getValue());
        }
        java.util.Collections.sort(views, new java.util.Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return nodeCordMap.get(node1.data).getX().compareTo(nodeCordMap.get(node2.data).getX());
            }
        });
        return views;
    }

    @Override
    protected Integer getKeyCordinateBasis(final Node node) {
        return nodeCordMap.get(node.data).getX();
    }

    @Override
    protected abstract boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord);

}

public class TopBinTreeViewImpl  extends AbstractVerticalView  {

    @Override
    protected boolean compareCoordinate(Coordinate firstCord, Coordinate secondCord) {
        return firstCord.isVerticallyAbove(secondCord);
    }

}
