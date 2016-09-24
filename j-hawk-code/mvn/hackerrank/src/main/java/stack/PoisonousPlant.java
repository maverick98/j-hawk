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
public class PoisonousPlant {

    public static void main(String args[]) {
     //   Scanner scanner = new Scanner(System.in);

   
      
        Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\poison.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimpleTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

  
        PoisonousPlant ste = new PoisonousPlant();

        Integer result = ste.test(scanner);
        System.out.println(result);
        //  dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\steout1.txt", result);
    }

    public void show(List<Integer> result) {
        for (Integer res : result) {
            System.out.println(res);
        }
    }

    private static void dump(String file, List<Integer> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Integer res : result) {
                bw.write(res + "\n");
            }
            bw.close();
        } catch (IOException ex) {

        }
    }

    public Integer test(Scanner scanner) {

        Integer result = 1;
        int n = -1;
        Stack<Integer> stack1 = new Stack<>();

        while (true) {
            String line = scanner.nextLine();
            if (n == -1) {
                n = Integer.parseInt(line.trim());

            } else {

                line = line.trim();
                StringTokenizer strTok = new StringTokenizer(line, " ");
                while (strTok.hasMoreTokens()) {
                    stack1.push(Integer.parseInt(strTok.nextToken()));
                }

                break;

            }

        }
        result = calculate(stack1);
        return result;

    }

    public Integer calculate(Stack<Integer> stack1) {

        Integer result = -1;
        Stack<Integer> stack2 = new Stack<Integer>();

        Integer stack1Size = stack1.size;

        Integer stack2Size = stack2.size;

        while (true) {
            stack1Size = stack1.size;

            stack2Size = stack2.size;
            if (stack1Size > stack2Size) {
                fillStack2(stack1, stack2);
                result++;
                stack2Size = stack2.size;
                if (stack1Size.equals(stack2Size)) {
                    break;
                }
            } else if (stack1Size < stack2Size) {
                fillStack1(stack1, stack2);
                result++;
                stack1Size = stack1.size;
                if (stack1Size.equals(stack2Size)) {
                    break;
                }
            }

        }

        return result;

    }

    public void fillStack1(Stack<Integer> stack1, Stack<Integer> stack2) {

        while (true) {

            if (stack2.isEmpty()) {
                break;
            }

            Integer popped = stack2.pop();
            stack1.push(popped);
            Integer top = stack2.peek();
            if (top == null || popped < top) {
                stack2.pop();
            }
        }
    }

    public void fillStack2(Stack<Integer> stack1, Stack<Integer> stack2) {

        while (true) {

            if (stack1.isEmpty()) {
                break;
            }

            Integer popped = stack1.pop();
            Integer top = stack1.peek();
            if (top == null || popped <= top) {
                stack2.push(popped);
            }
        }
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
