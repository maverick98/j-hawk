

import java.util.logging.Level;
import java.util.logging.Logger;
import org.commons.ds.queue.BlockingQueue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author manosahu
 */
public class BlockingQueueTest {

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
    public void testBlockingQueue() throws Exception {
      final BlockingQueue<String> bk = new BlockingQueue<>(3);
      Thread thread1 = new Thread(){
          @Override
          public void run(){
              try {
                  bk.enqueue("abc");
              } catch (InterruptedException ex) {
                  Logger.getLogger(BlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
      
      Thread thread2 = new Thread(){
          @Override
          public void run(){
              try {
                  bk.enqueue("def");
              } catch (InterruptedException ex) {
                  Logger.getLogger(BlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
      
      Thread thread3 = new Thread(){
          @Override
          public void run(){
              try {
                  bk.enqueue("ghi");
              } catch (InterruptedException ex) {
                  Logger.getLogger(BlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
      
       Thread thread31 = new Thread(){
          @Override
          public void run(){
              try {
                  bk.enqueue("ghaai");
              } catch (InterruptedException ex) {
                  Logger.getLogger(BlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
       
         Thread thread311= new Thread(){
          @Override
          public void run(){
              try {
                  bk.enqueue("ghaai");
              } catch (InterruptedException ex) {
                  Logger.getLogger(BlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
      thread1.start();
      thread2.start();
      thread3.start();
      thread31.start();
      thread311.start();
      
       thread1.join();
      thread2.join();
      thread3.join();
      thread31.join();
      thread311.join();
      
      Thread thread4 = new Thread(){
          @Override
          public void run(){
              try {
                  bk.dequeue();
              } catch (InterruptedException ex) {
                  Logger.getLogger(BlockingQueueTest.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
      thread4.start();
      thread4.join();
      
    }

    private static void sop(Object obj) {
        System.out.println(obj.toString());
    }

    private static void sopn(Object obj) {
        System.out.print(obj.toString());
    }
}
