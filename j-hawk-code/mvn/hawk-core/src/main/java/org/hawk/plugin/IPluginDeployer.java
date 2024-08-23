/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.plugin;



/**
 *
 * @author manosahu
 */
public interface IPluginDeployer {

    public boolean deploy() throws Exception;
    public boolean unDeploy() throws Exception;
}
