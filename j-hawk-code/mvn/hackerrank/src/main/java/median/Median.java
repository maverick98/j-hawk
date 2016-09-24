package median;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manosahu
 */
public class Median {

    public static void main(String args[]) {
        Median mung = new Median();
        List<String> result = new ArrayList<>();
        // Scanner scanner = new Scanner(System.in);
        //  result = mung.findRunningMedian(scanner);
        int[] input1 = {24, 2, 45, 20, 56, 75, 2, 56, 99, 53, 12};
        List<Node> input = new ArrayList<>();
        for (int i = 0; i < input1.length; i++) {
            input.add(new Node(input1[i], i));
        }
        Node medianNode = mung.findRank(input, 1);

        System.out.println(medianNode);

        medianNode = mung.findRank(input, 2);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 3);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 4);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 5);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 6);
        System.out.println(medianNode);
        
        
         medianNode = mung.findRank(input, 7);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 8);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 9);
        System.out.println(medianNode);
        medianNode = mung.findRank(input, 10);
        System.out.println(medianNode);
       

    }

    public Node findRank(List<Node> input, Integer m) {
        return rank(input, m);
    }

    private Node rank(List<Node> input, Integer m) {
        return this.select(input, 0, input.size(), m);
    }

    public Node select(List<Node> input, int start, int end, Integer m) {
        Node x = input.get(start);
        partition(start, end, input, x);
        if (x.idx  == m) {
            return x;
        }
        if (x.idx  > m) {
            return select(input, start, x.idx, m);
        } else {
            return select(input, x.idx +1, end, m);
        }

    }

    private void partition(int start, int end, List<Node> input, Node x) {
        int i = start;
        int j = end - 1;
        while (i <= j) {
            while (input.get(i).compareTo(x) <= 0) {

                i++;
                if(i >= end){
                    break;
                }
            }

            while (input.get(j).compareTo(x) > 0) {

                j--;
                if(j < 0){
                    break;
                }
            }
            if (i <= j) {
                swap(input, i, j);
                i++;
                j--;
            }
        }
        swap(input, i - 1, x.idx);
    }

    private void swap(List<Node> input, int i, int j) {
        input.get(i).idx = j;
        input.get(j).idx = i;

        Node tmp = input.get(j);
        input.set(j, input.get(i));
        input.set(i, tmp);

    }

    static class Node implements Comparable<Node> {

        Integer data;
        Integer idx;

        public Node(Integer data, Integer idx) {
            this.data = data;
            this.idx = idx;
        }

        @Override
        public String toString() {
            return "Node{" + "data=" + data + ", idx=" + idx + '}';
        }

        @Override
        public int compareTo(Node t) {
            return this.data.compareTo(t.data);
        }

    }
}
