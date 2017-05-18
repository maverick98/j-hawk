package org.commons.ds.heap.binomial;

/**
 *
 * @author manosahu
 * @param <K>
 * @param <V>
 */
public class BinomialHeap<K extends Comparable, V> {

    private BinomialNode<K, V> head;

    public BinomialHeap(BinomialNode<K, V> head) {
        this.head = head;
    }

    public BinomialHeap() {

    }

    public BinomialNode<K, V> getHead() {
        return head;
    }

    public void setHead(BinomialNode<K, V> head) {
        this.head = head;
    }

    /**
     * This traverses the root list Hence the complexity is BigOh(lg (n))
     *
     * @return
     */
    public BinomialNode<K, V> findHeapMinimum() {

        BinomialNode<K, V> y = null;
        K min = null;
        BinomialNode<K, V> curHead = this.getHead();
        while (curHead != null) {
            if (min == null) {
                min = curHead.getKey();
                y = curHead;
            }
            if (min.compareTo(curHead.getKey()) > 0) {
                min = curHead.getKey();
                y = curHead;
            }
            curHead = curHead.getSibling();
        }

        return y;
    }

    /**
     * This links y and z making z y's parent and thus increasing z 's degree by
     * 1 finished testing.
     *
     * @param y
     * @param z
     */
    public boolean link(BinomialNode<K, V> y, BinomialNode<K, V> z) {
        if (y == null || z == null) {
            throw new IllegalArgumentException("non null values of y an z are required for linking.");
        }

        y.setParent(z);
        y.setSibling(z.getChild());
        z.setChild(y);
        z.incrementDegree();
        return true;

    }

    public BinomialHeap<K, V> merge(BinomialHeap<K, V> binomialHeap2) {
        if (binomialHeap2 == null) {
            throw new IllegalArgumentException("non null values of binomialHeap1 and binomialHeap2 are required for mergeing.");
        }
        BinomialHeap<K, V> binomialHeap = new BinomialHeap<>();
        BinomialNode<K, V> head1 = this.getHead();
        BinomialNode<K, V> head2 = binomialHeap2.getHead();
        BinomialNode<K, V> result = binomialHeap.getHead();
        BinomialNode<K, V> runningHead = new BinomialNode<>();
        result = runningHead;
        while (true) {
            if (head1 == null && head2 == null) {
                break;
            }
            if (head1 == null) {
                runningHead.setSibling(head2);
                break;
            } else if (head2 == null) {
                runningHead.setSibling(head1);
                break;
            } else {
                // both are non null... do something
                if (head1.getDegree() <= head2.getDegree()) {
                    runningHead.setSibling(head1);
                    head1 = head1.getSibling();
                } else {
                    runningHead.setSibling(head2);
                    head2 = head2.getSibling();
                }
                runningHead = runningHead.getSibling();
            }

        }
        result = result.getSibling();
        binomialHeap.setHead(result);
        return binomialHeap;
    }

    public BinomialHeap<K, V> union(BinomialHeap<K, V> thatBinomialHeap) {

        BinomialHeap<K, V> finalBinomialHeap = new BinomialHeap<>();
        BinomialHeap<K, V> thisBinomialHeap = this;
        finalBinomialHeap = thisBinomialHeap.merge(thatBinomialHeap);
        BinomialNode<K, V> previousNode = null;
        BinomialNode<K, V> currentNode = finalBinomialHeap.getHead();
        BinomialNode<K, V> nextNode = currentNode.getSibling();
        while (nextNode != null) {
            if (this.shouldSkip(currentNode, nextNode)) {
                previousNode = currentNode;
                currentNode = nextNode;

            } else if (currentNode.getKey().compareTo(nextNode.getKey()) <= 0) {
                currentNode.setSibling(nextNode.getSibling());
                this.link(nextNode, currentNode);
            } else {
                if (previousNode == null) {
                    finalBinomialHeap.setHead(nextNode);
                }
               // previousNode.setSibling(nextNode);
                this.link(currentNode, nextNode);
                currentNode = nextNode;
            }

            nextNode = currentNode.getSibling();
        }

        return finalBinomialHeap;
    }

    private boolean shouldSkip(BinomialNode<K, V> currentNode, BinomialNode<K, V> nextNode) {
        boolean degreeOfNextNodeSameAsDegreeOfCurrentNode = currentNode.getDegree() != nextNode.getDegree();
        boolean degreeOfSiblingOfNextNodeSameAsDegreeOfCurrentNode = (nextNode.getSibling() != null && nextNode.getSibling().getDegree() == currentNode.getDegree());
        return degreeOfNextNodeSameAsDegreeOfCurrentNode || degreeOfSiblingOfNextNodeSameAsDegreeOfCurrentNode;

    }

    public BinomialHeap<K, V> insert(BinomialNode<K, V> node) {

        BinomialHeap<K, V> hdash = new BinomialHeap<>();
        hdash.setHead(node);
        BinomialHeap<K, V> result = this.union(hdash);
        
        return result;
    }

    public BinomialNode<K, V> extractMin() {

        BinomialHeap<K, V> binomialHeap = null;
        BinomialNode<K, V> minNode = this.findHeapMinimum();
        

        return null;
    }

    public boolean decreaseKey(BinomialNode<K, V> node, K newK) {

        return false;
    }

    public boolean delete(BinomialHeap<K, V> binomialHeap1, BinomialNode<K, V> node) {

        BinomialHeap<K, V> binomialHeap = null;

        return true;
    }

    public String toRootsUI() {
        StringBuilder sb = new StringBuilder();
        BinomialNode<K, V> head = this.getHead();
        while (head != null) {
            sb.append(head.getKey());
            sb.append("->");
            head = head.getSibling();
        }

        return sb.toString();

    }

}
