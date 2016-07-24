/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

/**
 *
 * @author manosahu
 * @param <T>
 */
public class GraphServiceImpl<T> implements IGraphService<T> {

    @Override
    public void dfsVisit(Graph<T> graph) {
        new DFSVisitor() {
            @Override
            public void onVisit(Node node) {
                System.out.println(node);
            }

        }.visit(graph.getNodes().get(0));
    }

    @Override
    public boolean hasEulerianPath(Graph<T> graph) {
        final OddDegreeCountHolder oddDegreeCountHolder = new OddDegreeCountHolder();
        new DFSVisitor() {
            @Override
            public void onVisit(Node node) {
                System.out.println(node);
                if (node.isOddDegree()) {
                    oddDegreeCountHolder.incrementCount();
                }
            }

        }.visit(graph.getNodes().get(0));
        return oddDegreeCountHolder.getCount() == 2;
    }

    private static class OddDegreeCountHolder {

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean incrementCount() {
            this.setCount(this.getCount() + 1);
            return true;
        }

    }

    @Override
    public boolean hasEulerianCircuit(Graph<T> graph) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasHamiltonianPath(Graph<T> graph) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasHamiltonianCircuit(Graph<T> graph) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
