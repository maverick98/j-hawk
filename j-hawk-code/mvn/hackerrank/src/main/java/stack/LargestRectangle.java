package stack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class LargestRectangle {

    public static void main(String args[]) {
        LargestRectangle largestRectangle = new LargestRectangle();

         Scanner scanner = new Scanner(System.in);
      
         /*
          Scanner scanner = null;
        try {
            scanner = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\larestrect1.txt"));
        } catch (java.io.FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Waiter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
*/

        Long result = largestRectangle.test(scanner);
        System.out.println(result);

    }

    public Long test(Scanner scanner) {

        Integer n = null;
        Long result = null;

        Long input[] = null;

        while (true) {
            String line = scanner.nextLine();
            line = line.trim();
            if (n == null) {

                n = Integer.parseInt(line);
                input = new Long[n];

            } else {
                StringTokenizer strTok = new StringTokenizer(line, " ");
                int i = 0;
                while (strTok.hasMoreTokens()) {
                    input[i++] = (Long.parseLong(strTok.nextToken()));
                }

                break;

            }

        }
        result = this.calculate(input);
        return result;

    }

    public Long calculate(final Long[] input) {
        Long currentMaxArea = Long.MIN_VALUE;
        Long minHeight = Long.MAX_VALUE;
        Stack<Long> stack = new Stack<>();

        for (int i = 0; i < input.length; i++) {

            Long next = input[i];
            Long maxAreaDueToIncreasingFunctions = new Long(0);
            Long maxAreaDueToDecreasingFunctions = new Long(0);
            Long peek = stack.peek();
            if (!stack.isEmpty()) {
                if (peek > next) {
                    maxAreaDueToDecreasingFunctions = this.calculateDecreasingFunctions(stack, next);
                } else {
                    maxAreaDueToIncreasingFunctions = this.calculateIncreasingFunctions(stack, next);
                }
            }else{
                maxAreaDueToIncreasingFunctions = this.calculateIncreasingFunctions(stack, next);
            }
            stack.push(next);
            if (next < minHeight) {
                minHeight = next;
            }
            Long areaDueToMinHeight = this.calculateAreaDueToMinHeight(minHeight, stack);

            currentMaxArea = this.maxBetween(currentMaxArea, maxAreaDueToIncreasingFunctions, maxAreaDueToDecreasingFunctions, areaDueToMinHeight);

        }

        return currentMaxArea;
    }

    private Long calculateAreaDueToMinHeight(Long minHeight, Stack<Long> stack) {
        Long areaDueToMinHeight = minHeight * stack.size;
        return areaDueToMinHeight;
    }

    private Long calculateDecreasingFunctions(Stack<Long> stack, Long next) {
        Long max = new Long(0);
        Long cnt = new Long(1);
        Stack<Long> backup = new Stack<>();
        if (stack.isEmpty()) {
            return max;
        }
        Long last = next;
        while (!stack.isEmpty()) {
            Long current = stack.peek();

            if (current < next) {

                break;
            }
            cnt++;
            backup.push(stack.pop());
            max = this.maxBetween(max, next * cnt);
            last = current;
        }
        if (cnt == 1) {
            max = new Long(0);
        }

        while (!backup.isEmpty()) {
            stack.push(backup.pop());
        }
        return max;
    }

    private Long calculateIncreasingFunctions(Stack<Long> stack, Long next) {

        Long max = next;
        Long cnt = new Long(1);
        Stack<Long> backup = new Stack<>();
        if (stack.isEmpty()) {
            return next;
        }
        Long localMinima = next;
        Long tmpMax = new Long(0);

        while (!stack.isEmpty()) {
            Long current = stack.peek();

            if (current == 1 || current > localMinima) {

                tmpMax = this.calculateDecreasingFunctions(stack, localMinima);
                if (tmpMax > 0) {
                    tmpMax = tmpMax - localMinima;
                }

                break;
            }
            cnt++;
            backup.push(stack.pop());
            max = this.maxBetween(max, current * cnt);
            localMinima = current;
        }
        if (cnt == 1) {
            max = next * cnt;
        }

        while (!backup.isEmpty()) {
            stack.push(backup.pop());
        }
        tmpMax = tmpMax + localMinima * cnt;
        max = this.maxBetween(max,tmpMax);
        return max;
    }

    private Long maxBetween(Long area1, Long area2, Long area3, Long area4) {
        return Math.max(area1, Math.max(area2, Math.max(area3, area4)));
    }

    private Long maxBetween(Long area1, Long area2) {
        return Math.max(area1, area2);
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
