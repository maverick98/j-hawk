/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.string;

import java.util.HashSet;
import java.util.TreeSet;

/**
 *
 * @author manosahu
 */
public class BruteForce implements StringSearchAlgo {

    @Override
    public int search(String text, String pattern) {
        HashSet s=null;
        TreeSet aa;
        int N = text.length();
        int M = pattern.length();
        int result = -1;
        for (int i = 0; i <= N - M; i++) {

            int j = 0;
            for (; j < M; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == M) {
                result = i;
            }

        }

        return result;
    }
}
