/*
 * This file is part of j-hawk
 *  
 * 

 */
package org.commons.ds.graph.service;

import org.commons.ds.graph.visitor.AbstractDFSVisitor;
import org.commons.ds.graph.Graph;
import org.commons.ds.graph.visitor.AbstractBFSVisitor;
import org.commons.ds.graph.visitor.EulerianDFSVisitor;
import org.commons.ds.graph.visitor.ShowBFSVisitor;
import org.commons.ds.graph.visitor.ShowDFSVisitor;

/**
 *
 * @author manosahu
 * @param <T>
 */
public class GraphServiceImpl<T> implements IGraphService<T> {

    private AbstractDFSVisitor showDFSVisitor = new ShowDFSVisitor();
    private AbstractBFSVisitor showBFSVisitor = new ShowBFSVisitor();
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

    public AbstractBFSVisitor getShowBFSVisitor() {
        return showBFSVisitor;
    }

    public void setShowBFSVisitor(AbstractBFSVisitor showBFSVisitor) {
        this.showBFSVisitor = showBFSVisitor;
    }

    @Override
    public void bfsVisit(Graph<T> graph) {
        this.getShowBFSVisitor().visit(graph.getNodes().get(0));
    }

    @Override
    public void dfsVisit(Graph<T> graph) {
        this.getShowDFSVisitor().visit(graph.getNodes().get(0));
    }

    @Override
    public boolean hasEulerianPath(Graph<T> graph) {
        this.getEulerianDFSVisitor().setNode(graph.getNodes().get(0));
        return this.getEulerianDFSVisitor().hasEulerianPath();
    }

    @Override
    public boolean hasEulerianCircuit(Graph<T> graph) {
        return this.getEulerianDFSVisitor().hasEulierianCircuit();
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
