package array;

import java.util.*;

public class Rotation {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        Integer input[] = test(scanner);
        show(input);
    }
    private static void show(Integer input[]){
        int i=0;
        for(;i<input.length-1;i++){
            System.out.print(input[i]+ " ");
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
                for(int i=0;i<n;i++){
                    input[i] = Integer.parseInt(strTok.nextToken());;
                }
                if (count == 1) {
                    leftRotate(input, leftR);

                    break;
                }
            }

        }

        return input;

    }

    private static void leftRotate(Integer arr[], int d) {
        int n = arr.length;
         int i, j, k, temp;
        for (i = 0; i < gcd(d, n); i++) 
        {
            /* move i-th values of blocks */
            temp = arr[i];
            j = i;
            while (1 != 0) 
            {
                k = j + d;
                if (k >= n) 
                    k = k - n;
                if (k == i) 
                    break;
                arr[j] = arr[k];
                j = k;
            }
            arr[j] = temp;
        }

    }
    
    private static int gcd(int a, int b) 
    {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }
    
    private static void leftRototate(Integer input[]){
        Integer first = input[0];
        for(int i=1;i<input.length;i++){
            input [i-1] = input[i];
        }
        input [input.length -1] = first;
    }
}
