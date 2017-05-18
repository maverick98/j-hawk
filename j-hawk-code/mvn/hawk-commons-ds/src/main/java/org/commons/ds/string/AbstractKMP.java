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
public abstract class AbstractKMP implements KnuthMorrisPrat {

    protected static final int R = 256;

    private int dfa[][];

    private String pattern;

    public AbstractKMP() {

    }

    @Override
    public int[][] getDfa() {
        return dfa;
    }

    @Override
    public void setDfa(int[][] dfa) {
        this.dfa = dfa;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int getPatternLength() {
        return this.getPattern().length();
    }

    @Override
    public int search(final String text, final String pattern) {
        KnuthMorrisPrat kmp = this.constructDFA(pattern);
        int N = text.length();
        int j = 0;
        int i = 0;
        int M = kmp.getPatternLength();
        for (; i < N && j < M; i++) {

            j = kmp.getDfa()[text.charAt(i)][j];
        }
        int result = -1;
        if (j == M) {
            result = i - M;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < this.getDfa()[i].length; j++) {
                sb.append("  ");
                sb.append(this.getDfa()[i][j]);

            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public  String toUI(){
        StringBuilder sb = new StringBuilder();
         for(int i=0; i< this.getPatternLength();i++){
             sb.append("  ");
             sb.append(this.getPattern().charAt(i));
         }
         sb.append("\n");
         
         for (int i = 0; i < R; i++) {
            StringBuilder row = new StringBuilder(); 
            int emptyRow = 0;
            row.append((char)i);
            for (int j = 0; j < this.getDfa()[i].length; j++) {
                if(this.getDfa()[i][j] == 0){
                    emptyRow = emptyRow +1;
                }
                
                row.append("  ");
                row.append(this.getDfa()[i][j]);

            }
            if(emptyRow != this.getPatternLength()){
                sb.append(row.toString());
                sb.append("\n");
            }
            
        }
        
        return sb.toString();
    }

    public abstract KnuthMorrisPrat constructDFA(String pattern);

}
