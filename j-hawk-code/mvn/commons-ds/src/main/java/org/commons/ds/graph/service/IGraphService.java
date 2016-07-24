/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph.service;

import org.commons.ds.graph.Graph;

/**
 *
 * @author manosahu
 */
public interface IGraphService<T> {

   

    public void dfsVisit(Graph<T> graph);

    public boolean hasEulerianPath(Graph<T> graph);

    public boolean hasEulerianCircuit(Graph<T> graph);

    public boolean hasHamiltonianPath(Graph<T> graph);

    public boolean hasHamiltonianCircuit(Graph<T> graph);

}
