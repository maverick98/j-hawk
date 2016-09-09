package stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author manosahu
 */
public class Bracket {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
      /* 
       Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\sample1.txt"));
        } catch (Exception ex) {

        }
        */
        
        List<String> result = test(scanner);
        show(result);
        //dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\output1.txt", result);

    }

    private static void show(List<String> result) {
        for (String res : result) {
            System.out.println(res);
        }
    }

    private static void dump(String file, List<String> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int i=0;
            for (;i<result.size() -1;i++) {
                bw.write(result.get(i)+"\n");
            }
            bw.write(result.get(i));
            bw.close();
        } catch (IOException ex) {

        }
    }

    public static List<String> test(Scanner scanner) {

        List<String> result = new ArrayList<>();

        int n = -1;
        int count = 0;

        while (true) {
            String line = scanner.nextLine();
            if (n == -1) {
                n = Integer.parseInt(line.trim());

            } else {
                count++;
                line = line.trim();
                boolean balanced = isCorr(line);
                if (balanced) {
                    result.add("YES");
                } else {
                    result.add("NO");
                }
                if (count == n) {

                    break;
                }
            }

        }
        return result;

    }

    public static boolean isCorr(String str) {
        boolean  result = false;
        String status= "";
        Stack<String> stack = new Stack<>();
        for(int i=0;i<str.length();i++){
            String d =str.charAt(i)+"";
            if(isOpeningBracket(d)){
                stack.push(d);
            }
            if(isClosingBracket(d)){
                if(stack.isEmpty()){
                    status = "error";
                    break;
                }
                String opening = myMatchingOpeningBracket(d);
                if(!opening.equals(stack.top())){
                     status = "error";
                    break;
                }else{
                    stack.pop();
                }
            }
        }
        if(!stack.isEmpty()){
            status = "error";
        }
        return  !"error".equals(status);
    }
    public boolean isValidData(String input){
        String valid [] = new String [] {""};
        return true;
    }
    public static String myMatchingOpeningBracket(String input){
        String result;
        if(input.equals("}")){
            result = "{";
        }else if(input.equals("]")){
            result = "[";
        }else if(input.equals(")")){
            result = "(";
        }else{
            result = null;
        }
        return result;
    }
    public static boolean isOpeningBracket(String c){
        return "{".equals(c) ||"[".equals(c) || "(".equals(c);
    }
    public  static boolean isClosingBracket(String c){
        return "}".equals(c) ||"]".equals(c) || ")".equals(c);
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
                data.remove(size-1);
                size--;
            }
            return topObj;
        }

       
        public <T> T top() {

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
