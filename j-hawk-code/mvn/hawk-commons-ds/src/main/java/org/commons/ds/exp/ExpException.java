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
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.commons.ds.exp;

/**
 *
 * @author manosahu
 */
public class ExpException extends Exception {

    private static final long serialVersionUID = 1L;

    public ExpException() {
    }

    public ExpException(Object msg) {
        super(msg.toString());
    }

    public ExpException(Throwable t) {
        super(t);
    }

    public ExpException(Object msg, Throwable t) {
        super(msg.toString(), t);
    }

   
}