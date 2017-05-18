/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.executor.cache;

import org.commons.implementor.IVisitor;

/**
 *
 * @author manosahu
 */
public interface IScriptCacheVisitor extends IVisitor{
    
     public void visit(IScriptCache scriptCache) ;
}
