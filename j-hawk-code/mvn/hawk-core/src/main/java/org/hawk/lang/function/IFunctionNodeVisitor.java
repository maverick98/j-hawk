/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.function;

/**
 *
 * @author manosahu
 */
public interface IFunctionNodeVisitor {
    public  boolean onVisit(FunctionNode functionNode);
}
