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
public class SimpleTextEditor {

    public static void main(String args[]) {

         Scanner scanner = new Scanner(System.in);
/*
         Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\ste.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimpleTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
*/
        SimpleTextEditor ste = new SimpleTextEditor();

        List<String> result =ste.test(scanner);
        ste.show(result);
      //  dump("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\steout1.txt", result);
    }
    
    public void show(List<String> result){
        for(String res : result){
            System.out.println(res);
        }
    }
    
      private static void dump(String file, List<String> result) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String res : result) {
                bw.write(res+"\n");
            }
            bw.close();
        } catch (IOException ex) {
            
        }
    }

    public List<String> test(Scanner scanner) {

        List<String> result = new ArrayList<>();

        int n = -1;
        Stack<State> stack = new Stack<>();

        int count = 0;
        String text = new String();
        while (true) {
            String line = scanner.nextLine();
            if (n == -1) {
                n = Integer.parseInt(line.trim());

            } else {
                count++;
                line = line.trim();
                StringTokenizer strTok = new StringTokenizer(line, " ");
                Integer opr = Integer.parseInt(strTok.nextToken());
                String toAppend;
                if (opr == 1 ) {
                    toAppend = strTok.nextToken();
               
                    State state = new State();
                    state.opr = 1;
                    text = text +toAppend;
                    state.text = text;
                    stack.push(state);
                } else if (opr == 2) {
                    Integer k = Integer.parseInt(strTok.nextToken());
                    text = text.substring(0, text.length() - k);
                    State state = new State();
                    state.opr = 2;
                    state.text = text;
                    stack.push(state);
                   

                } else if (opr == 3) {
                     Integer k = Integer.parseInt(strTok.nextToken());
                     result.add(text.charAt(k-1)+"");
                }else if( opr == 4){
                    stack.pop();
                    if(!stack.isEmpty()){
                        State state = stack.top();
                        text = state.text;
                    }else{
                        text = "";
                    }
                }
                if (count == n) {

                    break;
                }
            }

        }
        return result;

    }

    static class State{
        private int opr;
        private String text;

        @Override
        public String toString() {
            return "State{" + "opr=" + opr + ", text=" + text + '}';
        }
        public boolean shouldReactToUndo(){
            return opr ==1 || opr ==2;
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
