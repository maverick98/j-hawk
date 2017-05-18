/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.function;


import org.hawk.lang.object.IObjectScript;

/**
 *
 * @author manosahu
 */
public interface IFunctionExecutor {
 
    public IObjectScript execute(FunctionInvocationInfo functionInvocationInfo) throws Exception;
}
