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
public class ScrewedKMP extends AbstractKMP {

    @Override
    public KnuthMorrisPrat constructDFA(String pattern) {
        AbstractKMP kmp = new ScrewedKMP();
        kmp.setPattern(pattern);
        kmp.setDfa(new int[R][pattern.length()]);
        kmp.getDfa()[pattern.charAt(0)][0] = 1;
        int M = pattern.length();

        for (int j = 1; j < M; j++) {

            String current = kmp.getPattern().substring(0, j);

            for (int k = 0; k < R; k++) {

                char cr = (char) k;

                String currentTemp = current + cr;
                int curLength = currentTemp.length();
                int pos = 0;

                if (kmp.getPattern().startsWith(currentTemp)) {
                    pos = curLength;

                } else {
                    for (int i = j; i >= 1; i--) {

                        String suffix = currentTemp.substring(curLength - i, curLength);
                        if (currentTemp.startsWith(suffix)) {
                            pos = suffix.length();
                            break;
                        }
                    }
                }

                kmp.getDfa()[k][j] = pos;

            }
        }

        System.out.println(kmp.toUI());

        return kmp;
    }

}
