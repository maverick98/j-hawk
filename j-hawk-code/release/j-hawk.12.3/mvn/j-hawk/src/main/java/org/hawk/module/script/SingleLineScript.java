


package org.hawk.module.script;


import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.Implementor;
import org.hawk.module.annotation.Implementors;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.annotation.SingleTonSetter;
import org.hawk.pattern.PatternMatcher;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.module.annotation.PostCreateScript;
import org.hawk.module.script.type.Variable;

/**
 *
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */

@Implementors
(
    {

            
            @Implementor(clazz="EchoScript",parsingSequence=1),
            @Implementor(clazz="ThinkScript",parsingSequence=2),
            @Implementor(clazz="ExecFunctionScript",parsingSequence=3),
            @Implementor(clazz="ExecModuleScript",parsingSequence=4),
            @Implementor(clazz = "ArrayDeclScript",parsingSequence=5),
            @Implementor(clazz = "BreakScript",parsingSequence=6),
            @Implementor(clazz = "ReturnScript",parsingSequence=7),
            @Implementor(clazz = "StructureScript",parsingSequence=8),
            @Implementor(clazz = "ExecParallelSingleLineScript",parsingSequence=9),
            @Implementor(clazz = "ExecBackgroundSingleLineScript",parsingSequence=10),
            @Implementor(clazz = "ReadLineScript",parsingSequence=11),
            @Implementor(clazz = "LocalVarDeclScript",parsingSequence=12),
            @Implementor(clazz = "SingleLineCommentScript",parsingSequence=13),
            @Implementor(clazz = "AssignmentScript",parsingSequence=14)
    }
)
public  class SingleLineScript extends AbstractScript{

    private static final HawkLogger logger = HawkLogger.getLogger(SingleLineScript.class.getName());
    @Override
    public IScript execute() throws HawkException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SingleLineScript(){
        this.outerMultiLineScript = new MultiLineScript();
    }

    public static boolean addScripts(int i , String scriptStr ,MultiLineScript  multiLineScript) throws HawkException{

        boolean scriptFound = false;
        

        try{
            scriptFound = addSingleLineScripts(i, scriptStr, multiLineScript);
        }catch(HawkException ex){
             throw new HawkException ("Unrecognized characters {"+scriptStr+"} at line no {"+(++i)+"}");
        }

        if(!scriptFound && (scriptStr!=null && !scriptStr.trim().isEmpty())){
            throw new HawkException ("Unrecognized characters {"+scriptStr+"} at line no {"+(++i)+"}");
        }
        return true;
    }

    private static boolean addSingleLineScripts(int i , String scriptStr,MultiLineScript multiLineScript) throws HawkException{

        Map<Class,SingleLineScript> cachedSingleLineScripts = SingleLineScriptFactory.getSingleLineScripts();

        for(Entry<Class,SingleLineScript> entry:cachedSingleLineScripts.entrySet()){

            Method [] methods = entry.getKey().getDeclaredMethods();

            Method scriptPatternMethod = null;
            Method createScriptMethod = null;
            Method postCreateScriptMethod = null;
            Method singleTonSetterMethod = null;
            

            for(Method method : methods){

                if(method.isAnnotationPresent(ScriptPattern.class)){
                    scriptPatternMethod = method;
                }

                if(method.isAnnotationPresent(CreateScript.class)){
                    createScriptMethod  = method;
                }
                if(method.isAnnotationPresent(PostCreateScript.class)){
                    postCreateScriptMethod  = method;
                }
                if(method.isAnnotationPresent(SingleTonSetter.class)){
                    singleTonSetterMethod  = method;
                }

            }
            try {
                Pattern pattern  = (Pattern) scriptPatternMethod.invoke(entry.getValue(), new Object[]{});
                Map<Integer,String> lineMatcherMap = PatternMatcher.match(pattern, scriptStr);
                if(lineMatcherMap != null){
                    SingleLineScript  script = (SingleLineScript)createScriptMethod.invoke(entry.getValue(), lineMatcherMap);
                    if(script!= null && script instanceof SingleLineCommentScript){
                        return true;
                    }
                    
                    if(singleTonSetterMethod!= null && script!= null){
                        singleTonSetterMethod.invoke(entry.getValue(), script);
                    }

                    if(script != null){
                        script.setLineNumber(i+1);
                        script.setOuterMultiLineScript(multiLineScript);
                        if(postCreateScriptMethod != null){
                            postCreateScriptMethod.invoke(script);
                        }
                        multiLineScript.addScript(i, script);
                        
                        return true;
                    }
                }
            } catch (Throwable ex) {
                logger.severe("error while parsing {"+scriptStr+"} ..."+ex.getMessage());
                System.out.println("error while parsing {"+scriptStr+"} ..."+ex.getMessage());
                throw new HawkException(ex);
            }
        }
        return false;
    }

    @Override
    public boolean isVariable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Variable getVariable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setVariableValue(Variable value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Variable getVariableValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setVariable(Variable value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IScript copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MultiLineScript findNearestOuterMLScript() {
        return this.getOuterMultiLineScript();
                
    }

    @Override
    public String toUI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}




