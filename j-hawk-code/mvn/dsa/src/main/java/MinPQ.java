
import java.util.Scanner;

/**
 *
 * @author manosahu
 */
public class MinPQ {

    private Integer pq[];

    private int n;

    
    public MinPQ(){
        this(1);
    }
    public MinPQ(int initialCapacity){
        this.pq = new Integer[initialCapacity+1];
        this.n=0;
    }
    public MinPQ(Integer[] nodes) {
        this.pq = new Integer[nodes.length + 1];
        for (int i = 0; i < nodes.length; i++) {
            this.pq[i + 1] = nodes[i];
        }
        this.n = this.pq.length - 1;
        int mid = this.parent(this.size());
        for (int i = mid; i >= 1; i--) {
            this.sink(i);
        }
    }

    private void resize(int newCapacity){
        Integer tmp[] = new Integer[newCapacity];
        for(int i=1;i<=this.size();i++){
            tmp[i] = this.pq[i];
        }
        this.pq= tmp;
    }
    private void swap(int i, int j) {
        Integer tmp = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = tmp;
    }

    public int lessThan(int i, int j) {
        int result;
        if (this.pq[i] <= this.pq[j]) {
            result = i;
        } else {
            result = j;
        }
        return result;
    }

    public int lessThan(int i, int j, int k) {
        return this.lessThan(i, this.lessThan(j, k));
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int left(int i) {
        return 2 * i;
    }

    public int right(int i) {
        return this.left(i) + 1;
    }

    public int parent(int i) {
        return i / 2;
    }

    public Integer min() {
        if(this.isEmpty()){
            return null;
        }
        return this.pq[1];
    }

    public void sort() {
        int cnt = this.size();
        for (int i = 0; i < cnt; i++) {
            this.delMin();
        }
    }

    public Integer delMin() {
        if(this.isEmpty()){
            return null;
        }
        Integer min = null;
        min = this.pq[1];
        this.swap(1, n);
        n = n - 1;
        this.sink(1);
        return min;
    }

    public void swim(int i) {
        while (i > 1) {
            int parent = this.parent(i);
            int least = this.lessThan(i, parent);
            if (i == least) {
                this.swap(i, parent);
                i = parent;
            }

        }
    }

    public void sink(int i) {
        int right = this.right(i);
        while (right <= this.size()) {
            int left = this.left(i);

            int least = this.lessThan(i, left, right);
            if (i != least) {
                this.swap(i, least);
                i = least;
                right = this.right(i);
            } else {
                break;
            }

        }
    }

    public void insert(Integer node) {
        if (n == pq.length - 1) resize(2 * pq.length);
        this.pq[n + 1] = node;
        n = n + 1;
        this.swim(n);
    }

    public static void main(String[] args) {
        //MinPQ<String> pq = new MinPQ<>(5);
        Scanner scan = new Scanner(System.in);
        Integer nodes[] = new Integer[]{33, -1, 44, 331, 43};

        MinPQ pq = new MinPQ(nodes);

       
        Integer nodes1[] = new Integer[]{33, -1, 44, 331, 43};

        MinPQ pq1 = new MinPQ(nodes1);
        pq1.insert(-444);
        sop(pq1.min());
        sop(pq1.size());

    }

    private static void sop(Object object) {
        System.out.print(object.toString());
    }
}
