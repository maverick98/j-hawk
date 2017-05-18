/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.commons.ds.operator.OperatorConstant.ASSIGN;
import static org.commons.ds.operator.OperatorConstant.EQUAL;
import static org.commons.ds.operator.OperatorConstant.GREATER;
import static org.commons.ds.operator.OperatorConstant.GREATERTHANEQUAL;
import static org.commons.ds.operator.OperatorConstant.LESSER;
import static org.commons.ds.operator.OperatorConstant.LESSTHANEQUAL;
import static org.commons.ds.operator.OperatorConstant.MINUS;
import static org.commons.ds.operator.OperatorConstant.REFERENCE;
import org.commons.xml.XMLUtil;

/**
 *
 * @author manosahu
 */
public class OperatorCache {

    private static final OperatorCache theInstance = new OperatorCache();

    private OperatorCache() {
        this.loadSupportedOperators();
    }

    public static OperatorCache getInstance() {
        return theInstance;
    }
    private Operators supportedOperators;
    
    private Map<String,IOperator> operatorMap;
    
    public IOperator findBySymbol(String symbol){
        return operatorMap.get(symbol);
    }
    public Map<String, IOperator> getOperatorMap() {
        return operatorMap;
    }

    public void setOperatorMap(Map<String, IOperator> operators) {
        this.operatorMap = operators;
    }

  
    public Operators getSupportedOperators() {
        return supportedOperators;
    }

    public boolean loadSupportedOperators(){
        
       boolean status= false;
       Operators supportedOperators  = null;
        try {
            
            supportedOperators = XMLUtil.unmarshal(Thread.currentThread().getContextClassLoader().getResourceAsStream("language/operators.xml"), Operators.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(OperatorCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        operatorMap = this.convertToIOperators(supportedOperators);
       return status;
    }
    private Map<String,IOperator> convertToIOperators(Operators sOperators){
        
        Map<String,IOperator> result = new HashMap<String,IOperator>();
        if(sOperators != null){
            for(Operator supportedOperator : sOperators.getOperator()){
                
                //IOperator operator = new DefaultOperator(supportedOperator);
                IOperator operator = OperatorFactory.getInstance().findBySymbol(supportedOperator.getSymbol());
                if(operator!= null){
                    operator.setOperator(supportedOperator);
                    result.put(operator.getSymbol(),operator);
                }
            }
        }
        return result;
    }
    public void setSupportedOperators(Operators supportedOperators) {
        this.supportedOperators = supportedOperators;
    }
    private Map<String, IOperator> matchingCache = new HashMap<String, IOperator>();
    private Map<String, IOperator> exactlyMatchingCache = new HashMap<String, IOperator>();

    public IOperator getMatchingOperator(String key) {
        return this.matchingCache.get(key);
    }

    public Map<String, IOperator> getMatchingCache() {
        return matchingCache;
    }

    public void setMatchingCache(Map<String, IOperator> matchingCache) {
        this.matchingCache = matchingCache;
    }

    public Map<String, IOperator> getExactlyMatchingCache() {
        return exactlyMatchingCache;
    }

    public void setExactlyMatchingCache(Map<String, IOperator> exactlyMatchingCache) {
        this.exactlyMatchingCache = exactlyMatchingCache;
    }

    public IOperator getExactlyMatchingOperator(String key) {
        return this.exactlyMatchingCache.get(key);
    }

    public void putMatchingOperator(String key, IOperator opr) {
        this.matchingCache.put(key, opr);
    }

    public void putExactlyMatchingOperator(String key, IOperator opr) {
        this.exactlyMatchingCache.put(key, opr);
    }
    
      //@Override
    public IOperator matchesExactly(String substr) {
         if (OperatorCache.getInstance().getExactlyMatchingCache().containsKey(substr)) {
            return OperatorCache.getInstance().getExactlyMatchingCache().get(substr);
        }
        IOperator resultOperator = matches(substr);
        if (resultOperator != null) {

            if (!resultOperator.getSymbol().equals(substr)) {
                resultOperator = null;
            }
        }

        OperatorCache.getInstance().getExactlyMatchingCache().put(substr, resultOperator);
        return resultOperator;
    }

   // @Override
    public IOperator matches(String substr) {
        if (OperatorCache.getInstance().getMatchingCache().containsKey(substr)) {
            return OperatorCache.getInstance().getMatchingCache().get(substr);
        }
        IOperator resultOperator = null;
        for (IOperator opr : OperatorCache.getInstance().getOperatorMap().values()) {
            if (substr.startsWith(opr.getSymbol())) {
                resultOperator = opr;
                if (opr.getSymbol().equals(MINUS)) {
                    if (substr.startsWith(REFERENCE)) {
                        
                        resultOperator = OperatorCache.getInstance().findBySymbol(REFERENCE);
                    }
                } else if (opr.getSymbol().equals(LESSER)) {
                    if (substr.startsWith(LESSTHANEQUAL)) {
                        
                        resultOperator = OperatorCache.getInstance().findBySymbol(LESSTHANEQUAL);
                    }
                } else if (opr.getSymbol().equals(GREATER)) {
                    if (substr.startsWith(GREATERTHANEQUAL)) {
                        resultOperator = OperatorCache.getInstance().findBySymbol(GREATERTHANEQUAL);
                    }
                } else if (opr.getSymbol().equals(ASSIGN)) {
                    if (substr.startsWith(EQUAL)) {
                        resultOperator = OperatorCache.getInstance().findBySymbol(EQUAL);
                    }
                }
                break;
            }
        }
        OperatorCache.getInstance().putMatchingOperator(substr, resultOperator);
        return resultOperator;
    }

}
