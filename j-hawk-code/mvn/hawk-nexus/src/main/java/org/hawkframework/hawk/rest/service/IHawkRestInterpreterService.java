/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawkframework.hawk.rest.service;

/**
 *
 * @author reemeeka
 */
public interface IHawkRestInterpreterService {
    public String interpret(String src )throws Exception;
    public String compile(String src) throws Exception;
}
