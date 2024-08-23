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
package org.hawk.lang.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import org.commons.string.StringUtil;
import org.commons.ds.element.Element;

import org.hawk.lang.IScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.logger.HawkLogger;
import static org.hawk.ds.exp.HawkDSConstant.EXEC_PATTERN;
/**
 *
 * @author msahu
 */
public class ExecParameterScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecParameterScript.class.getName());

    private Map<Integer,IObjectScript> paramMap = new TreeMap<>();

    private String params ;


    public List<IObjectScript> getParamList(){
        List<IObjectScript> result = new ArrayList<>();
        this.paramMap.entrySet().stream().forEach((entry) -> {
            result.add(entry.getValue());
        });
        return result;
    }
    public Map<Integer,IObjectScript> getParamMap(){
        return this.paramMap;
    }
    public Map<Integer, IObjectScript> cloneParamMap() {

        logger.info("Executing in thread {"+Thread.currentThread().getName()+"}");
        Map<Integer,IObjectScript> clonedParamMap = new TreeMap<>();
        this.paramMap.entrySet().stream().forEach((entry) -> {
            clonedParamMap.put(entry.getKey(),(IObjectScript)entry.getValue().copy());
        });

        return clonedParamMap;

    }

    public void setParamMap(Map<Integer, IObjectScript> paramMap) {
        this.paramMap = paramMap;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public ExecParameterScript(){
        
    }

    @Override
    public IScript execute() throws Exception {

        this.paramMap.clear();
        
        this.params = this.params.trim();
        if(this.params.endsWith(",")){
            throw new Exception("Invalid parameters");
        }

        if(!this.params.isEmpty())
        {
            String paramsArr[] = this.params.split(",");

            if(paramsArr == null || paramsArr.length == 0 ){
                throw new Exception("Invalid parameters");

            }else{
                int i = 1;

                for(String param :paramsArr){
                   param = param.replaceAll("~", ",");
                   IObjectScript result = this.evaluateLocalVariable(param);

                   this.paramMap.put(i,result);
                   //sb.append(result.mangle());
                   i++;
                }
            }

        }
        
        return LocalVarDeclScript.createDummyBooleanScript();

    }

    /**
     * `exec test(`exec aa()`,2)`,3,\"India\",`exec test()`
     * This returns <tt>ExecParameterScript</tt> from exec param matcher map.
     * @param lineExecParamMatcherMap
     * @return
     */
    
    public  IScript createScript(String params) throws Exception{

        if(params == null){
            return null;
        }
        String preProcessedParams;
        try {
            preProcessedParams = preProcessParams(params);
        } catch (Exception ex) {

           throw new Exception("invalid parameters {"+params+"}");
        }
        
        if(!preProcessedParams.isEmpty())
        {
            String paramsArr[] = preProcessedParams.split(",");

            if(paramsArr == null || paramsArr.length == 0 ){
                throw new Exception("could not recognize parameters {"+params+"}");

            }

        }
        ExecParameterScript execParameterScript =new ExecParameterScript();
        execParameterScript.setParams(preProcessedParams);
        
        return execParameterScript;
    }

   
    private static String preProcessParams(String params) throws Exception{
        params =  params.replaceAll("\\)\\s*`", ")}");
        params =  params.replaceAll("`", "{");
        StringBuilder result = new StringBuilder();
        Matcher execMatcher = EXEC_PATTERN.matcher(params);
        boolean execFound = false;
        int i =0;
        while(execMatcher.find()){
            execFound = true;
            
            String preExecData= params.substring(0,execMatcher.start());
            String execData = StringUtil.parseDelimeterData(params.substring(execMatcher.start()), '{', '}');
            execData = execData.replaceAll(",","~");
            
            result.append(preExecData);
            result.append("{");
            result.append(execData);
            result.append("}");
            int tmp = preExecData.length()+execData.length();
            int j = params.indexOf("}", tmp-1);
            i=j+1;
            
            params = params.substring(i);
            execMatcher = EXEC_PATTERN.matcher(params);
            
            
        }
        if(!execFound){
            result = new StringBuilder(params);
            //result = params;
        }else{
            //result = result.replaceAll("~", ",");
            if(!params.trim().isEmpty()){
                
                result.append(params);
            }
            
        }
        return result.toString();
    }
    

}
