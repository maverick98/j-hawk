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
package org.hawk.executor;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hawk.data.perf.PerfDataProcessor;
import org.common.di.AppContainer;
import org.commons.ds.element.Element;
import org.commons.ds.element.ElementBinaryTree;
import org.commons.ds.tree.bintree.PreOrderBinTreeIterator;

import org.hawk.executor.cache.AbstractScriptCacheVisitor;
import org.hawk.executor.cache.IScriptCache;
import org.hawk.executor.cache.IScriptCacheProvider;
import org.hawk.executor.cache.multiline.function.IFunctionScriptCache;
import org.hawk.executor.cache.multiline.multilinecomment.IMultiLineCommentScriptCache;
import org.hawk.executor.cache.multiline.structure.IStructureDefinitionScriptCache;
import org.hawk.executor.command.HawkCommandParser;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.input.HawkInput;
import org.hawk.lang.IScript;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.task.SubTaskContainer;
import org.hawk.output.HawkOutput;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>This parses the hawk script and runs the same. This reads through the hawk
 * file and parses the function definition and hence creates the function script
 * out of them. The function script contains all the scripts written inside the
 * function definition.
 *
 * <p>This is the place where all the global variables and aliases are stored.
 *
 * <p>This parses through target application's <tt>IModule</tt> implementations
 * to cache all the modules <tt>IModule</tt> and subtask
 * <tt>SubTaskContainer</tt>
 * Note: The developer has to annotate the tasks with <tt>SubTask</tt> to
 * register with Hawk framework as subtask failing to which Hawk will ignore the
 * same.
 *
 *
 * @VERSION 1.0 7 Apr, 2010
 * @author msahu
 * @see HawkUsage
 * @see IModule
 * @see SubTaskContainer
 * @see SubTask
 */
   
   
public class DefaultScriptExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(DefaultScriptExecutor.class.getName());
    
    @Autowired(required = true)
       
    private HawkOutput hawkOutput;
    
    @Autowired(required = true)
       
    private HawkInput hawkInput;
    
    
    @Autowired(required = true)
       
    private IMultiLineCommentScriptCache multilineCommentScriptCache;
    
    @Autowired(required = true)
       
    private IFunctionScriptCache functionScriptCache;
    
    @Autowired(required = true)
       
    private IStructureDefinitionScriptCache structureDefinitionCache;
    
    
    @Autowired(required = true)
       
    private IScriptCacheProvider scriptCacheProvider;

    public IMultiLineCommentScriptCache getMultilineCommentScriptCache() {
        return multilineCommentScriptCache;
    }

    public void setMultilineCommentScriptCache(IMultiLineCommentScriptCache multilineCommentScriptCache) {
        this.multilineCommentScriptCache = multilineCommentScriptCache;
    }

    public IFunctionScriptCache getFunctionScriptCache() {
        return functionScriptCache;
    }

    public void setFunctionScriptCache(IFunctionScriptCache functionScriptCache) {
        this.functionScriptCache = functionScriptCache;
    }

    public IStructureDefinitionScriptCache getStructureDefinitionCache() {
        return structureDefinitionCache;
    }

    public void setStructureDefinitionCache(IStructureDefinitionScriptCache structureDefinitionCache) {
        this.structureDefinitionCache = structureDefinitionCache;
    }

    public HawkOutput getHawkOutput() {
        return hawkOutput;
    }

    public void setHawkOutput(HawkOutput hawkOutput) {
        this.hawkOutput = hawkOutput;
    }

    public HawkInput getHawkInput() {
        return hawkInput;
    }

    public void setHawkInput(HawkInput hawkInput) {
        this.hawkInput = hawkInput;
    }

    
    
   

    /**
     * ***************methods*******************
     */
    public void reset() throws Exception {

        this.getHawkInput().reset();
       
        scriptCacheProvider.visitScriptCache(new AbstractScriptCacheVisitor() {

          

            @Override
            public void visit(IScriptCache scriptCache) {
                scriptCache.reset();
            }

           
        });
        Element.reset();
    }

    

    /**
     * This checks file type extension of the input hawk script file
     *
     * @param scriptFile hawk script file
     * @return <tt>true</tt> if the hawk script file has .hawk extension
     *
     * @throws org.hawk.exception.Exception if the hawk script file does not
     * have .hawk extension
     */
    private boolean validateHawkScript(File scriptFile) throws Exception {
        
        if (scriptFile == null ) {

            throw new Exception("Unrecognized hawk script file");
        }

        return true;
    }

    /**
     * This interprets the hawk script file.
     *
     * @return
     * @throws org.hawk.exception.Exception if there is no main function
     * defined in hawk script or if the script is not interpretable.
     */
    public boolean interpret() throws Exception {

       
        boolean status = false;
        
      
        
        
        if (this.functionScriptCache.getMainFunction() == null || !this.getFunctionScriptCache().isIterpretable()) {

            throw new Exception("Could not find main function");
        }

        IScript mainOutput = this.functionScriptCache.getMainFunction().executeDefaultForLoopScript(null);
        this.getHawkOutput().writeEchoOutput(mainOutput.toJson());
        this.getHawkOutput().closeAll();
        
        if (AppContainer.getInstance().getBean( HawkCommandParser.class).shouldCollectPerfData()) {
            PerfDataProcessor hawkPerfDataCollector = AppContainer.getInstance().getBean( PerfDataProcessor.class);
            hawkPerfDataCollector.dump(true);

            status = hawkPerfDataCollector.processPerfData();

        }

        return status;

    }

    private boolean compileInternal() throws Exception {

       
         scriptCacheProvider.cache();
         scriptCacheProvider.visitScriptCache(new AbstractScriptCacheVisitor() {

          

            @Override
            public void visit(IScriptCache scriptCache) {
                try {
                    scriptCache.cache();
                } catch (Exception ex) {
                    Logger.getLogger(DefaultScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

           
        });

        return true;
    }

    /**
     * This parses the hawk script file for syntax check.
     *
     * @param scriptFile hawk script file
     * @return <tt>true</tt> if syntax check is successful
     * <tt>false</tt> otherwise
     * @throws org.hawk.exception.Exception if it is not valid hawk script
     * file
     */
    public boolean compile(File file) throws Exception {


        if (this.validateHawkScript(file)) {

            this.getHawkInput().readScript(file);

            this.compileInternal();

        } else {
            throw new Exception("could not read hawk script from the stream");
        }


        return true;
    }

    /**
     * This parses the hawk script file for syntax check.
     *
     * @param scriptFile hawk script file
     * @return <tt>true</tt> if syntax check is successful
     * <tt>false</tt> otherwise
     * @throws org.hawk.exception.Exception if it is not valid hawk script
     * file
     */
    public boolean compile(String scriptFile) throws Exception {

        this.getHawkInput().readScript(scriptFile);

        this.compileInternal();

        return true;
    }

   

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
