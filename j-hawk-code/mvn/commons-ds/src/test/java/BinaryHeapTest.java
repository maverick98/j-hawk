
import org.commons.ds.heap.BinaryHeap;
import org.commons.ds.heap.MaxHeap;
import org.commons.ds.heap.MinHeap;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class BinaryHeapTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testMin() throws Exception {

        Integer ints[] = new Integer[]{33, -1, 44, 331, 43};

        MinHeap minHeap = new MinHeap(ints);
        Assert.assertEquals(minHeap.min(), new Integer(-1));
        minHeap.insert(-444);
        Assert.assertEquals(minHeap.min(), new Integer(-444));
        MaxHeap maxHeap = new MaxHeap(ints);
        Assert.assertEquals(maxHeap.max(), new Integer(331));
        Comparable sortedKeys[] = maxHeap.sort();
        for (int i = 1; i < sortedKeys.length; i++) {
            sopn(sortedKeys[i] + " ");
        }

    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }

    private static void sopn(Object obj) {
        System.out.print(obj.toString());
    }
}
