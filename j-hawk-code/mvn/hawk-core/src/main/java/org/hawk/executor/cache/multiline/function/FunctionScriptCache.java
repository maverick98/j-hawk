/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.executor.cache.multiline.function;

import java.util.List;
import java.util.Map;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.MultiLineScriptCache;
import org.hawk.lang.function.AbstractFunctionNodeVisitor;
import org.hawk.lang.function.FunctionNode;
import org.hawk.lang.function.FunctionScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.thread.ExecParallelSingleLineScript;
import org.hawk.logger.HawkLogger;
import org.hawk.util.HawkUtil;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class FunctionScriptCache extends MultiLineScriptCache implements IFunctionScriptCache {

    private static final HawkLogger logger = HawkLogger.getLogger(FunctionScriptCache.class.getName());
    /**
     * A flag to check whether a hawk script can be interpreted.
     */
    private boolean iterpretable = false;
    /**
     * A reference to main function which is the entry point of hawk script
     * execution.
     */
    private FunctionScript mainFunction = null;

    @Autowired(required = true)
       
    private GlobalFunctionTemplateScriptCache functionTemplateScriptCache;

    public GlobalFunctionTemplateScriptCache getFunctionTemplateScriptCache() {
        return functionTemplateScriptCache;
    }

    public void setFunctionTemplateScriptCache(GlobalFunctionTemplateScriptCache functionTemplateScriptCache) {
        this.functionTemplateScriptCache = functionTemplateScriptCache;
    }

    @Override
    public boolean isIterpretable() {
        return iterpretable;
    }

    public void setIterpretable(boolean iterpretable) {
        this.iterpretable = iterpretable;
    }

    @Override
    public FunctionScript getMainFunction() {
        return mainFunction;
    }

    public void setMainFunction(FunctionScript mainFunction) {
        this.mainFunction = mainFunction;
    }

    @Override
    public FunctionNode getRootFunctionNode() {
        return this.getFunctionTemplateScriptCache().getRootFunctionNode();
    }

    public void setRootFunctionNode(FunctionNode functionNode) {
        this.getFunctionTemplateScriptCache().setRootFunctionNode(functionNode);
    }

    @Override
    public boolean cache() throws Exception {

        this.getRootFunctionNode()
                .visitFunctionNode(
                        this.getRootFunctionNode(),
                        new AbstractFunctionNodeVisitor() {

                            @Override
                            public boolean onVisit(FunctionNode functionNode) {
                                boolean shouldIcontrolVisit = false;
                                try {
                                    createFunctionScript(functionNode.getValue());
                                } catch (Exception ex) {
                                    logger.error(ex);
                                }
                                return shouldIcontrolVisit;
                            }
                        }
                );
        return true;
    }

    public boolean createFunctionScript(FunctionScript functionScript) throws Exception {

        boolean parsed = FunctionScript.createFunctionScript(functionScript);
        if (parsed) {

            if (functionScript.isMainFunction()) {

                this.iterpretable = true;

                this.mainFunction = functionScript;
            }

        } else {
            throw new Exception("parsing failure");
        }
        return parsed;
    }

    @Override
    public FunctionScript findAnyFunctionScript(final String functionName) throws Exception {
        return this.getRootFunctionNode().findAnyFunctionScript(functionName);

    }

    @Override
    public List<FunctionScript> findAllOverloadedFunctionScripts(final String functionName) throws Exception {
       return this.getRootFunctionNode().findAllOverloadedFunctionScripts(functionName);

    }

    /**
     * This finds the <tt>FunctionScript</tt> for the input mangled function
     * name When invoked in threads other than main thread, this method clones
     * the function script and returns the same.
     *
     * @param functionName
     * @param paramMap
     * @return returns <tt>FunctionScript</tt>
     * @throws Exception
     * @see FunctionScript
     */
    @Override
    public FunctionScript findFunctionScript(String functionName, Map<Integer, IObjectScript> paramMap) throws Exception {

        if (functionName == null || functionName.isEmpty()) {

            throw new Exception("invalid function name");

        }

        FunctionScript functionScript = this.getRootFunctionNode().findFunctionScript(functionName, paramMap);

        if (functionScript != null) {
            if (!HawkUtil.isMainThread()) {

                //logger.info("Executing in thread {" + Thread.currentThread().getName() + "}");

                functionScript = this.cloneFunctionScript(functionScript);

            }
        }

        return functionScript;
    }

    /**
     * This clones input <tt>FunctionScript</tt>. This is used in parallel
     * execution of hawk functions
     *
     * @see ExecParallelSingleLineScript
     * @param functionScript
     * @return
     */
    public FunctionScript cloneFunctionScript(FunctionScript functionScript) {

        if (functionScript == null) {

            return null;

        }

        FunctionScript clonedFunctionScript = functionScript.createFunctionTemplate();

        try {

            this.createFunctionScript(clonedFunctionScript);

        } catch (Exception ex) {

            logger.info(ex.getMessage());

            return null;

        }

        return clonedFunctionScript;
    }

    @Override
    public boolean isInsideMultiLineScript(int i) {
        return this.getFunctionTemplateScriptCache().isInsideMultiLineScript(i);
    }

    @Override
    public boolean reset() {

        this.setRootFunctionNode(null);
        this.setIterpretable(false);
        this.setMainFunction(null);
        return true;
    }

    @Override
    @Create
    public MultiLineScriptCache create() {
        return AppContainer.getInstance().getBean(FunctionScriptCache.class);
    }

}
