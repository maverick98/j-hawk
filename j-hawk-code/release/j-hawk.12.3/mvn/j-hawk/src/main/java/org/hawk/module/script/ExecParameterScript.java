
package org.hawk.module.script;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import org.hawk.ds.Element;
import org.hawk.exception.HawkException;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.CreateScript;
import org.hawk.util.HawkUtil;

/**
 *
 * @author msahu
 */
public class ExecParameterScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecParameterScript.class.getName());

    private Map<Integer,IScript> paramMap = new TreeMap<Integer,IScript>();

    private String params ;


    public Map<Integer,IScript> getParamMap(){
        return this.paramMap;
    }
    public Map<Integer, IScript> cloneParamMap() {

        logger.info("Executing in thread {"+Thread.currentThread().getName()+"}");
        Map<Integer,IScript> clonedParamMap = new TreeMap<Integer,IScript>();
        for(Entry<Integer,IScript> entry:this.paramMap.entrySet()){
            clonedParamMap.put(entry.getKey(),entry.getValue().copy());
        }

        return clonedParamMap;

    }

    public void setParamMap(Map<Integer, IScript> paramMap) {
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
    public IScript execute() throws HawkException {

        this.paramMap.clear();
        
        this.params = this.params.trim();
        if(this.params.endsWith(",")){
            throw new HawkException("Invalid parameters");
        }

        if(!this.params.isEmpty())
        {
            String paramsArr[] = this.params.split(",");

            if(paramsArr == null || paramsArr.length == 0 ){
                throw new HawkException("Invalid parameters");

            }else{
                int i = 1;

                for(String param :paramsArr){
                   param = param.replaceAll("~", ",");
                   IScript result = this.evaluateLocalVariable(param);

                   this.paramMap.put(i,result);
                   //sb.append(result.mangle());
                   i++;
                }
            }

        }
        
        return LocalVarDeclScript.createDummyScript();

    }

    /**
     * `exec test(`exec aa()`,2)`,3,\"India\",`exec test()`
     * This returns <tt>ExecParameterScript</tt> from exec param matcher map.
     * @param lineExecParamMatcherMap
     * @return
     */
    @CreateScript
    public static IScript createScript(String params) throws HawkException{

        if(params == null){
            return null;
        }
        String preProcessedParams = params;
        try {
            preProcessedParams = preProcessParams(params);
        } catch (HawkException ex) {

           throw new HawkException("invalid parameters {"+params+"}");
        }
        
        if(!preProcessedParams.isEmpty())
        {
            String paramsArr[] = preProcessedParams.split(",");

            if(paramsArr == null || paramsArr.length == 0 ){
                throw new HawkException("could not recognize parameters {"+params+"}");

            }

        }
        ExecParameterScript execParameterScript =new ExecParameterScript();
        execParameterScript.setParams(preProcessedParams);
        
        return execParameterScript;
    }

    public static void main(String args[]) throws HawkException{
        String data = "`exec test ( `exec test(1,3 )`,2 )`  + `exec test(2,3)`,3,`exec tesaaaast(223,311)`";
        String data1 ="3,4";
        System.out.println(preProcessParams(data));
    }
    private static String preProcessParams(String params) throws HawkException{
        params =  params.replaceAll("\\)\\s*`", ")}");
        params =  params.replaceAll("`", "{");
        String result = "";
        Matcher execMatcher = Element.EXEC_PATTERN.matcher(params);
        boolean execFound = false;
        int i =0;
        while(execMatcher.find()){
            execFound = true;
            
            String preExecData= params.substring(0,execMatcher.start());
            String execData = HawkUtil.parseDelimeterData(params.substring(execMatcher.start()), '{', '}');
            execData = execData.replaceAll(",","~");
            
            result+=preExecData;
            result+="{";
            result+= execData;
            result+="}";
            int tmp = preExecData.length()+execData.length();
            int j = params.indexOf("}", tmp-1);
            i=j+1;
            
            params = params.substring(i);
            execMatcher = Element.EXEC_PATTERN.matcher(params);
            
            
        }
        if(!execFound){
            result = params;
        }else{
            //result = result.replaceAll("~", ",");
            if(!params.trim().isEmpty()){
                
                result += params;
            }
            
        }
        return result;
    }
    

}
