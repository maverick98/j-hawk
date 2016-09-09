package heap;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class Test {

    public static void main(String args[]) {

       //  Scanner scanner = new Scanner(System.in);

        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\minwait5.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(MinWaitingTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

      

        new Test().test(scanner);
    }

    public void test(Scanner scanner) {

        int n = -1;

        int count = 0;

        MinHeap arrivalMinHeap = new MinHeap();
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node thisOne, Node thatOne) {

                return thisOne.arrivalTime.compareTo(thatOne.arrivalTime);

            }
        };
        arrivalMinHeap.cmp = cmp;
        while (true) {
            String line = scanner.nextLine();
            if (n == -1) {
                n = Integer.parseInt(line.trim());

            } else {
                count++;
                line = line.trim();
                StringTokenizer strTok = new StringTokenizer(line, " ");
                BigInteger arrivalTime = new BigInteger(strTok.nextToken());
                BigInteger prepTime = new BigInteger(strTok.nextToken());

                Node node = new Node();
                node.arrivalTime = arrivalTime;

                node.prepTime = prepTime;
                arrivalMinHeap.insert(node);

                if (count == n) {

                    break;
                }
            }

        }
        BigInteger result = this.findMinAvg(arrivalMinHeap);
        System.out.println(result);

    }

    static class MinHeap {

        Node keys[];
        int n;
        Comparator cmp;

        public MinHeap() {
            this(1);
        }

        public MinHeap(int initialCapacity) {
            keys = new Node[initialCapacity + 1];
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public Node min() {
            if (this.isEmpty()) {
                return null;
            }
            return keys[1];
        }

        public Node delMin() {
            if (this.isEmpty()) {
                return null;
            }
            Node minNode = this.min();

            this.swap(1, n);
            n--;
            this.sink(1);

            return minNode;
        }

        public void insert(Node node) {
            if (n == keys.length - 1) {
                resize(2 * keys.length);
            }
            n++;
            keys[n] = node;

            this.swim(n);

        }

        private void resize(int newCapacity) {
            Node newKeys[] = new Node[newCapacity];
            //todo use system.arraycopy
            for (int i = 1; i <= n; i++) {
                newKeys[i] = keys[i];
            }
            keys = newKeys;
        }

        private int lesserBetween(int i, int j) {

            int tmp = cmp.compare(keys[i], keys[j]);
            int result;
            if (tmp <= 0) {
                result = i;
            } else {
                result = j;
            }
            /*
            if (keys[i].prepTime <= (keys[j].prepTime)) {
                result = i;
            } else {
                result = j;
            }
             */
            return result;
        }

        private int lesserBetween(int i, int j, int k) {

            return this.lesserBetween(i, lesserBetween(j, k));

        }

        private void swap(int i, int j) {

            Node tmp = keys[i];
            keys[i] = keys[j];
            keys[j] = tmp;

        }

        private int parent(int i) {
            return i / 2;
        }

        private int left(int i) {
            return 2 * i;
        }

        private int right(int i) {
            return left(i) + 1;
        }

        private void sink(int i) {

            while (true) {
                int left = this.left(i);

                if (left > n) {
                    break;
                }
                int right = this.right(i);
                int least = -1;
                if (right <= n) {
                    least = this.lesserBetween(i, left, right);
                } else {
                    least = this.lesserBetween(i, left);
                }
                if (i != least) {
                    this.swap(i, least);
                    i = least;
                } else {
                    break;
                }
            }

        }

        private void swim(int i) {

            while (true) {

                int parent = parent(i);
                if (parent < 1) {
                    break;
                }
                int least = this.lesserBetween(i, parent);
                if (i == least) {
                    this.swap(i, parent);
                    i = parent;;

                } else {
                    break;
                }
            }
        }

    }

    static class Node {

        BigInteger arrivalTime;
        BigInteger prepTime;
        BigInteger waitingTime;

        public Node() {

        }

        public Node(BigInteger arrivalTime, BigInteger prepTime) {
            this.arrivalTime = arrivalTime;
            this.prepTime = prepTime;
        }

        @Override
        public String toString() {
            return "Node{" + "arrivalTime=" + arrivalTime + ", prepTime=" + prepTime + ", waitingTime=" + waitingTime + '}';
        }

    }

    public BigInteger findMinAvg(MinHeap arrivalMinHeap) {
        BigInteger result = BigInteger.ZERO;
        BigInteger totalSize = BigInteger.valueOf(arrivalMinHeap.size());
        BigInteger totalSum = this.findMinSum(arrivalMinHeap);
        result = totalSum.divide(totalSize);
        return result;
    }

    public BigInteger findMinSum(MinHeap arrivalMinHeap) {
        BigInteger result = BigInteger.ZERO;

        Node curNode = arrivalMinHeap.delMin();
        BigInteger curElapsedTime = curNode.prepTime.add(curNode.arrivalTime);
        curNode.waitingTime = curNode.prepTime;
      //  System.out.println(curNode.waitingTime);
        result = result.add(curNode.waitingTime);
        MinHeap pizzaPrepMinHeap = new MinHeap();
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node thisOne, Node thatOne) {

                return thisOne.prepTime.compareTo(thatOne.prepTime);

            }
        };
        pizzaPrepMinHeap.cmp = cmp;

        while (true) {
            if (arrivalMinHeap.isEmpty() && pizzaPrepMinHeap.isEmpty()) {
                break;
            }
            boolean insideCurElapsed = false;
            while (!arrivalMinHeap.isEmpty() && arrivalMinHeap.min().arrivalTime.compareTo(curElapsedTime) < 0) {
                insideCurElapsed = true;
                Node node = arrivalMinHeap.delMin();
                pizzaPrepMinHeap.insert(node);

            }
            if (insideCurElapsed) {
                if (!pizzaPrepMinHeap.isEmpty()) {
                    Node nextNode = pizzaPrepMinHeap.delMin();
                    calculateWaitingTime(curNode, nextNode);
                    curNode = nextNode;
                   //   System.out.println(curNode.waitingTime);
                    result = result.add(nextNode.waitingTime);
                    curElapsedTime = curElapsedTime.add(nextNode.prepTime);

                }
            } else if (!arrivalMinHeap.isEmpty()) {
                Node nextNode = arrivalMinHeap.delMin();
                curNode = nextNode;
                curElapsedTime = curNode.prepTime.add(curNode.arrivalTime);
                curNode.waitingTime = curNode.prepTime;
              //    System.out.println(curNode.waitingTime);
                result = result.add(curNode.waitingTime);
            } else {
                while (!pizzaPrepMinHeap.isEmpty()) {
                    Node nextNode = pizzaPrepMinHeap.delMin();
                    calculateWaitingTime(curNode, nextNode);
                     curNode = nextNode;
                  //     System.out.println(curNode.waitingTime);
                    result = result.add(nextNode.waitingTime);
                    curElapsedTime = curElapsedTime.add(nextNode.prepTime);
                }
            }

        }
        System.out.println(result);
     //   System.out.println("cur elapsed time "+curElapsedTime);
        return result;
    }

    private void calculateWaitingTime(Node curNode, Node minNode) {

        BigInteger minNodeWaitingTime = curNode.waitingTime.add(curNode.arrivalTime).subtract(minNode.arrivalTime).add(minNode.prepTime);
        minNode.waitingTime = minNodeWaitingTime;
    }
}
