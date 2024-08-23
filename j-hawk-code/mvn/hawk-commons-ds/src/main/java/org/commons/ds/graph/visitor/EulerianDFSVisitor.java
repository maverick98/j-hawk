/*
 * This file is part of j-hawk
 *  
 * 

 */
package org.commons.ds.graph.visitor;

import org.commons.ds.graph.Node;

/**
 *
 * @author manosahu
 */
public class EulerianDFSVisitor extends AbstractDFSVisitor {

    private Node node;

    private  final OddDegreeCountHolder oddDegreeCountHolder = new OddDegreeCountHolder();
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
    
    public EulerianDFSVisitor(){
        
    }
    
    public EulerianDFSVisitor(Node node){
        this.node = node;
    }
    public boolean hasEulerianPath(){
        boolean result;
        this.visit(this.getNode());
        result =  oddDegreeCountHolder.getCount() == 2;
        oddDegreeCountHolder.reset();
        return result;
        
    }
    
    public boolean hasEulierianCircuit(){
        
        return false;
    }
    
    @Override
    public void onVisit(Node node) {
        
        if (node.isOddDegree()) {
            oddDegreeCountHolder.incrementCount();
        }
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
        public boolean reset(){
            this.setCount(0);
            return true;
        }

    }
}
