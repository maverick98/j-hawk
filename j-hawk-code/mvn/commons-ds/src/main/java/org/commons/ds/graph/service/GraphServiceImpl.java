/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph.service;

import org.commons.ds.graph.visitor.AbstractDFSVisitor;
import org.commons.ds.graph.Graph;
import org.commons.ds.graph.visitor.EulerianDFSVisitor;
import org.commons.ds.graph.visitor.ShowDFSVisitor;

/**
 *
 * @author manosahu
 * @param <T>
 */
public class GraphServiceImpl<T> implements IGraphService<T> {

    private AbstractDFSVisitor showDFSVisitor = new ShowDFSVisitor();
    private EulerianDFSVisitor eulerianDFSVisitor = new EulerianDFSVisitor();

    public EulerianDFSVisitor getEulerianDFSVisitor() {
        return eulerianDFSVisitor;
    }

    public void setEulerianDFSVisitor(EulerianDFSVisitor eulerianDFSVisitor) {
        this.eulerianDFSVisitor = eulerianDFSVisitor;
    }

    public AbstractDFSVisitor getShowDFSVisitor() {
        return showDFSVisitor;
    }

    public void setShowDFSVisitor(AbstractDFSVisitor showDFSVisitor) {
        this.showDFSVisitor = showDFSVisitor;
    }

    @Override
    public void dfsVisit(Graph<T> graph) {
        this.getShowDFSVisitor().visit(graph.getNodes().get(0));
    }

    @Override
    public boolean hasEulerianPath(Graph<T> graph) {
       this.getEulerianDFSVisitor().setNode(graph.getNodes().get(0));
       return  this.getEulerianDFSVisitor().hasEulerianPath();
    }

    

    @Override
    public boolean hasEulerianCircuit(Graph<T> graph) {
        return  this.getEulerianDFSVisitor().hasEulierianCircuit();
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
