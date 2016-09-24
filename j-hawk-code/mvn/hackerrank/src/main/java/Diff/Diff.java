

package Diff;

import java.util.Scanner;

/**
 *
 * @author manosahu
 */
public class Diff {

    public static void main(String args[]){
        
         Scanner scanner1 = null;
         Scanner scanner2 = null;
        try {
            scanner1 = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc1hr.txt"));
            scanner2 = new Scanner(new java.io.File("D:\\github\\j-hawk\\j-hawk\\j-hawk-code\\mvn\\hackerrank\\src\\main\\medianupdatetc1mine.txt"));
            int line =0;
            while(true){
                 String line1 = scanner1.nextLine();
                 String line2 = scanner2.nextLine();
                 line++;
                 if(line1.equals(line2)){
                     continue;
                 }else{
                     System.out.println("diff at line "+line+ ", "+line1 + " --- "+line2);
                 }
            }
        } catch (Exception ex) {
            
        }
    }
}
