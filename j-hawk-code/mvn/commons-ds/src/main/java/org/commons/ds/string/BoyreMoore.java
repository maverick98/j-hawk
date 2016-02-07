/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.string;

/**
 *
 * @author manosahu
 */
public class BoyreMoore implements StringSearchAlgo {

    //AAANEEDLEAAA
    //
    @Override
    public int search(String text, String pattern) {
        int result = -1;
        this.setPattern(pattern);
        this.buildAlphaArray();
        int N = text.length();
        int M = pattern.length();
        int skip = 0;
        for(int i=0 ; i<= N-M;i+= skip){
            skip = 0;
           
            for(int j = M-1; j >0;j--){
                if(text.charAt(i+j) !=  pattern.charAt(j)){
                    
                    skip = Math.max(-1, j-this.alpha[text.charAt(i+j)]);
                    break;
                }
            }
            if(skip ==0){
                return i;
            }
        }
        
        return result;
    }

    private int alpha[] = new int[256];
    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;

    }

    private boolean buildAlphaArray() {
        for (int i = 0; i < 256; i++) {
            alpha[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            alpha[pattern.charAt(i)] = i;
        }
        return true;
    }
    
    public static void main(String args[]){
        BoyreMoore bm = new BoyreMoore();
        int r = bm.search("FINDINAHAYSTACKNEEDLE", "NEEDLE");
        System.out.println(r);
    }

}
