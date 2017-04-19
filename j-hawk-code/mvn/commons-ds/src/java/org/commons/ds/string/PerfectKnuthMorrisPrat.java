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
public class PerfectKnuthMorrisPrat extends AbstractKMP {

    @Override
    public KnuthMorrisPrat constructDFA(String pattern) {
        AbstractKMP kmp = new PerfectKnuthMorrisPrat();
        kmp.setPattern(pattern);
        kmp.setDfa(new int[R][pattern.length()]);
        kmp.getDfa()[pattern.charAt(0)][0] = 1;
        int M = pattern.length();
        for (int X = 0, j = 1; j < M; j++) {

            for (int c = 0; c < R; c++) {
                kmp.getDfa()[c][j] = kmp.getDfa()[c][X];
            }

            kmp.getDfa()[pattern.charAt(j)][j] = j + 1;

            X = kmp.getDfa()[pattern.charAt(j)][X];

        }
        System.out.println(kmp.toUI());
        return kmp;
    }

   
    
    

}
