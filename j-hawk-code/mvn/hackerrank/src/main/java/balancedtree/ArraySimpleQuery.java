package balancedtree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class ArraySimpleQuery {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
         Scanner scanner = new Scanner(System.in);
        /*
         Scanner scanner;
        Integer input[] = null;
        try {
            scanner = new Scanner(new File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\arraysimpletc1.txt"));
            long start = System.currentTimeMillis();
            input = test(scanner);
            long diff = System.currentTimeMillis() - start;
            System.out.println("took " + diff + "ms");
        } catch (FileNotFoundException ex) {

        }
        */
        Integer input[] = test(scanner);
        System.out.println(Math.abs(input[0] - input[input.length - 1]));
        show(input);
    }

    private static void show(Integer input[]) {
        int i = 0;
        for (; i < input.length - 1; i++) {
            System.out.print(input[i] + " ");
        }
        System.out.print(input[i]);

    }

    public static Integer[] test(Scanner scanner) {

        int N = -1;
        int M = -1;

        Integer input[] = null;

        int count = 0;
        while (true) {
            String line = scanner.nextLine();
            StringTokenizer strTok = new StringTokenizer(line, " ");

            if (N == -1) {
                N = Integer.parseInt(strTok.nextToken());
                M = Integer.parseInt(strTok.nextToken());
                input = new Integer[N];

            } else if (count == 0) {
                for (int i = 0; i < N; i++) {
                    input[i] = Integer.parseInt(strTok.nextToken());;
                }
                count++;
            } else {

                int opr = Integer.parseInt(strTok.nextToken());
                int start = Integer.parseInt(strTok.nextToken());
                int end = Integer.parseInt(strTok.nextToken());
                if (opr == 1) {
                    leftRotate(input, 0, end, start);
                } else if (opr == 2) {
                    rightRotate(input, start - 1, N, N - end);
                }
                count++;
                if (count > M) {
                    break;
                }

            } //leftRotate(input,5,12, leftR);
            //rightRotate(input, 5,12, leftR);
            //break;

        }

        return input;

    }

    private static void leftRotate(Integer arr[], int start, int end, int d) {

        int i, j, k, temp;
        int gcd = gcd(d, end - start);
        for (i = 0; i < gcd; i++) {

            temp = arr[i + start];
            j = i + start;
            while (true) {
                k = j + d;
                if (k >= end) {
                    k = k - end + start;
                }
                if (k == i+start) {
                    break;
                }
                arr[j] = arr[k];
                j = k;
            }
            arr[j] = temp;
        }

    }

    private static void rightRotate(Integer arr[], int start, int end, int d) {

        int i, j, k, temp;
        int gcd = gcd(d, end - start);
        for (i = 0; i < gcd; i++) {

            temp = arr[end - i - 1];
            j = end  - i - 1;
            while (true) {
                k = j - d;
                if (k < start) {
                    k = k + end - start;
                }
                if (k == end  - i - 1) {
                    break;
                }
                arr[j] = arr[k];
                j = k;
            }
            arr[j] = temp;
        }

    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
