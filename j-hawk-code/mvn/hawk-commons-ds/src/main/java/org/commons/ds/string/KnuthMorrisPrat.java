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
public interface KnuthMorrisPrat extends StringSearchAlgo {

    public int[][] getDfa();

    public void setDfa(int[][] dfa);

    public String getPattern();

    public void setPattern(String pattern);

    public int getPatternLength();

    //public KnuthMorrisPrat construtDFA(String pattern);
}
