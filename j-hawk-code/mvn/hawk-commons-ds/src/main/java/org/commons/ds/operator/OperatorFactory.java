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

package org.commons.ds.operator;

import java.util.HashMap;
import java.util.Map;
import org.commons.implementor.InstanceVisitable;


/**
 *
 * @author manosahu
 */
public class OperatorFactory {

    private static final OperatorFactory theInstance = new OperatorFactory();
    
    private Map<String,IOperator> operators = new HashMap<String,IOperator>();
    
    private OperatorFactory(){
        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(IOperator.class);

        try {
            new OperatorImplVisitor().visit(instanceVisitable);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Error(ex);
        }
    }
    public static OperatorFactory getInstance(){
        return theInstance;
    }
    public IOperator findBySymbol(String symbol){
        return operators.get(symbol.trim());
    }
    public boolean isOperator(String symbol){
        IOperator operator = this.findBySymbol(symbol);
        return operator != null;
    }
    
    private  class OperatorImplVisitor extends AbstractOperatorImplVisitor{

        @Override
        public void onVisit(IOperator operator) {
            Opr opr= (Opr)operator.getClass().getAnnotation(Opr.class);
            if(opr!=null){
                operators.put(opr.symbol(), operator);
            }
        }
        
    }
}
