package stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class QueueUsingTwoStack {

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
       /* 
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\queuetestcase10.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimpleTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */    
        QueueUsingTwoStack ste = new QueueUsingTwoStack();

        List<String> result = ste.test(scanner);
        ste.show(result);
        //dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\qtestcase10mine.txt", result);
    }

    public void show(List<String> result) {
        for (String res : result) {
            System.out.println(res);
        }
    }

    private static void dump(String file, List<String> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String res : result) {
                bw.write(res + "\n");
            }
            bw.close();
        } catch (IOException ex) {

        }
    }

    public List<String> test(Scanner scanner) {

        List<String> result = new ArrayList<>();

        int n = -1;
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
//       / java.util.Stack<String> stack1 = new java.util.Stack<>();
       // java.util.Stack<String> stack2 = new java.util.Stack<>();
        int count = 0;

        while (true) {
            String line = scanner.nextLine();
            if (n == -1) {
                n = Integer.parseInt(line.trim());

            } else {
                count++;
                line = line.trim();
                StringTokenizer strTok = new StringTokenizer(line, " ");
                Integer opr = Integer.parseInt(strTok.nextToken());
               
                if (opr == 1) {
                    Integer next = Integer.parseInt(strTok.nextToken());
                    while(!stack1.isEmpty()){
                        stack2.push(stack1.pop());
                    }
                    stack1.push(next);
                    while(!stack2.isEmpty()){
                        stack1.push(stack2.pop());
                       
                    }
                    

                } else if (opr == 2) {
                    stack1.pop();
                } else if (opr == 3) {
                    //String k = stack1.top();
                    String k = stack1.peek();
                    result.add(k);
                }
                if (count == n) {

                    break;
                }
            }

        }
        return result;

    }

    static class Stack<T> {

        private final List<T> data = new ArrayList<T>();

        private int size = 0;

        public boolean push(T obj) {
            if (obj == null) {
                return false;
            }
            boolean status;
            status = data.add(obj);
            size++;
            return status;
        }

        public <T> T pop() {

            T topObj = null;
            if (size > 0) {
                topObj = (T) data.get(size - 1);
                //data.remove(topObj);
                data.remove(size - 1);
                size--;
            }
            return topObj;
        }

        public <T> T peek() {

            T topObj = null;
            if (size > 0) {
                topObj = (T) data.get(size - 1);
            }
            return topObj;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        @Override
        public String toString() {
            return this.isEmpty() ? "" : data.toString();
        }
    }
}
