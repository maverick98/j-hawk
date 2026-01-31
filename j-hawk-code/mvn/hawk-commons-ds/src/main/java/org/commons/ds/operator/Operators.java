/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.operator;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.commons.xml.XMLUtil;

/**
 *
 * @author manosahu
 */
@XmlRootElement
public class Operators {
    private List<Operator> operator = new ArrayList<>();

    public Operators() {
    }

    public List<Operator> getOperator() {
        return operator;
    }

    public void setOperator(List<Operator> operator) {
        this.operator = operator;
    }
    
  
   
    
    public static void main(String args[]){
        Operators supportedOperators = new Operators();
        List<Operator> list = new ArrayList<Operator>();
        Operator so = new Operator("+",4);
        list.add(so);
        Operator so1 = new Operator("-",4);
        list.add(so1);
        
        
        supportedOperators.setOperator(list);
        
        XMLUtil.marshal( supportedOperators,"operator.xml");
        
    }
    
}
