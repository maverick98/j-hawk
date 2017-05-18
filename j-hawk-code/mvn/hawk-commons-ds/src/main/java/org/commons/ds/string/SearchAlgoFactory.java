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
public class SearchAlgoFactory {

    /**
     * As we discuss more , we can add more enums here 
     * like boyremoore, rabin-karp  , monte karlo etc.
     */
    public static enum SearchAlgo {

        BRUTEFORCE, KMP, SCREWED_KMP;
    }

    public static StringSearchAlgo chose(final SearchAlgo searchAlgo) {
        StringSearchAlgo result = null;
        switch (searchAlgo) {
            case KMP:
                result = new PerfectKnuthMorrisPrat();
                break;
            case BRUTEFORCE:
                result = new BruteForce();
                break;
            case SCREWED_KMP:
                result = new ScrewedKMP();
                break;
        }
        return result;
    }
}
