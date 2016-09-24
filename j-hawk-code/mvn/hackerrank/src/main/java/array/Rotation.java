package array;

import java.util.*;

public class Rotation {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        Integer input[] = test(scanner);
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

        int n = -1;
        int leftR = -1;
        Integer input[] = null;

        int count = 0;
        while (true) {
            String line = scanner.nextLine();
            StringTokenizer strTok = new StringTokenizer(line, " ");

            if (n == -1) {
                n = Integer.parseInt(strTok.nextToken());
                leftR = Integer.parseInt(strTok.nextToken());
                input = new Integer[n];

            } else {
                count++;
                for (int i = 0; i < n; i++) {
                    input[i] = Integer.parseInt(strTok.nextToken());;
                }
                if (count == 1) {
                    //leftRotate(input,input.length, leftR);
                    rightRotate(input, input.length, leftR);
                    break;
                }
            }

        }

        return input;

    }

    private static void leftRotate(Integer arr[], int n, int d) {

        int i, j, k, temp;
        int gcd = gcd(d, n);
        for (i = 0; i < gcd; i++) {
            /* move i-th values of blocks */
            temp = arr[i];
            j = i;
            while (true) {
                k = j + d;
                if (k >= n) {
                    k = k - n;
                }
                if (k == i) {
                    break;
                }
                arr[j] = arr[k];
                j = k;
            }
            arr[j] = temp;
        }

    }

    private static void rightRotate(Integer arr[], int n, int d) {

        int i, j, k, temp;
        int gcd = gcd(d, n);
        for (i = 0; i < gcd; i++) {
            /* move i-th values of blocks */
            temp = arr[n - i - 1];
            j = n - i - 1;
            while (true) {
                k = j - d;
                if (k < 0) {
                    k = k + n;
                }
                if (k == n - i - 1) {
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
