/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
package org.hawk.di.spring;

/**
 *
 * @author user
 */
public interface SpringConstant{

    /**
     * moduleFactory
     *
     * @see org.hawk.module.ModuleFactory
     */
    String MODULEEXECUTOR = "moduleExecutor";
    /**
     * moduleFactory
     *
     * @see org.hawk.module.ModuleCache
     */
    String MODULECACHE = "moduleCache";
    /**
     * HAWKPERFDATACOLLECTOR
     */
    String HAWKPERFDATACOLLECTOR = "hawkPerfDataCollector";
    /**
     * hawkUsage
     */
    String HAWKCOMMANDPARSER = "hawkCommandParser";
    /**
     * scriptInterpreter
     */
    String SCRIPTEXECUTOR = "scriptExecutor";
    /**
     * scriptInterpreter
     */
    String DEFAULTSCRIPTEXECUTOR = "defaultScriptExecutor";
    /**
     * scriptInterpreter
     */
    String SCRIPTCOMPILER = "scriptCompiler";
    /**
     * for hawkconfigmanger
     */
    String HAWKMODULEPROPERTIESMANAGER = "hawkModulePropertiesManager";
    /**
     * for interoverflow
     */
    String INTEGEROVERFLOW = "integerOverflow";
    /**
     * for version
     */
    String VERSIONEXECUTOR = "versionExecutor";

    /**
     * for version
     */
    String SCRIPTHTMLEXECUTOR = "scriptHTMLExecutor";
    /**
     * for help
     */
    String HELPEXECUTOR = "helpExecutor";
    /**
     * for training
     */
    String TRAININGEXECUTOR = "trainingExecutor";
    /**
     * for test
     */
    String INTEGRATIONTESTEXECUTOR = "integrationTestExecutor";
    /**
     *
     */
    String INTERNALTESTFUNCTIONALEXECUTOR = "internalTestFunctionalExecutor";
    /**
     *
     */
    String INTERNALTESTPERFEXECUTOR = "internalTestPerfExecutor";
    /**
     * for gui
     */
    String GUIEXECUTOR = "guiExecutor";
    /**
     * for gui
     */
    String PLOTTINGEXECUTOR = "plottingExecutor";
    /**
     * main
     */
    String MAIN = "main";
    /**
     * HAWKTARGETSETTING
     */
    String HAWKTARGETSETTING = "hawkTargetSetting";
    /**
     * globalVarScriptCache
     */
    String GLOBALVARSCRIPTCACHE = "globalVarScriptCache";
    /**
     * globalVarScriptCache
     */
    String FUNCTIONSCRIPTCACHE = "functionScriptCache";
    /**
     * globalVarScriptCache
     */
    String GLOBALFUNCTIONTEMPLATESCRIPTCACHE = "globalfunctionTemplateScriptCache";
    
    String LOCALFUNCTIONTEMPLATESCRIPTCACHE = "localfunctionTemplateScriptCache";
    /**
     * globalVarScriptCache
     */
    String STRUCTUREDEFNSCRIPTCACHE = "structureDefnScriptCache";
    /**
     * globalVarScriptCache
     */
    String MULTILINECOMMENTSCRIPTCACHE = "multilineCommentScriptCache";
    /**
     * globalVarScriptCache
     */
    String ALIASSCRIPTCACHE = "aliasScriptCache";
    /**
     * globalVarScriptCache
     */
    String XMLSTRUCTSCRIPTCACHE = "xmlStructScriptCache";
    /**
     * SCRIPTCACHINGSEQUENCEPROVIDER
     */
    String SCRIPTCACHINGSEQUENCEPROVIDER = "scriptCachingSequenceProvider";
    /**
     * SCRIPTCACHINGSEQUENCEPROVIDER
     */
    String HAWKCONFIGSEQUENCEPROVIDER = "hawkConfigSequenceProvider";
   
    /**
     * SINGLELINESCRIPTSEQUENCEPROVIDER
     */
    String MULTILINESCRIPTVERTICALSEQUENCEPROVIDER = "multiLineScriptVerticalSequenceProvider";
    /**
     * defaultScriptCache
     */
    String DEFAULTSCRIPTCACHE = "defaultScriptCache";
    /**
     * MULTILINESCRIPTCACHE
     */
    String MULTILINESCRIPTCACHE = "multiLineScriptCache";
    /**
     * SINGLELINESCRIPTCACHE
     */
    String SINGLELINESCRIPTCACHE = "singleLineScriptCache";
    /**
     * DEFAULTSEQUENCEPROVIDER
     */
    String DEFAULTSEQUENCEPROVIDER = "defaultSequenceProvider";
    /**
     * qualifier
     */
    String DEFAULTQUALIFIER = "default";

    String CLAZZLOADERCONFIG = "clazzLoaderConfig";

    String EXCELMODULE = "excelModule";
    /**
     * qualifier
     */
    String HAWKOUTPUT = "hawkOutput";
    /**
     * HAWKTESTPROPERTOIES
     */
    String HAWKTESTPROPERTIES = "hawkTestProperties";
    
    /**
     * SOFTWARESERVICE
     */
    String SOFTWARESERVICE = "softwareServiceImpl";
    /**
     * HAWKTESTPROPERTOIES
     */
    String HAWKTEST = "hawkTest";
    /**
     * HAWKTESTPROPERTOIES
     */
    String INTERNALFUNCTIONALTESTRESULT = "internalFunctionalTestResult";
    /**
     * HAWKTESTPROPERTOIES
     */
    String INTERNALPERFTESTRESULT = "internalPerfTestResult";
    /**
     * HAWKTESTPROPERTOIES
     */
    String HAWKINPUT = "hawkInput";
    /**
     * HTMLJAVASERVICE
     */
    String HTMLJAVASERVICE = "htmlJavaServiceImpl";
    /**
     * HTMLJAVASERVICE
     */
    String INTERNALFUNCTESTHTMLJAVASERVICE = "InternalFuncTestHTMLJavaServiceImpl";
    /**
     * HTMLJAVASERVICE
     */
    String INTERNALPERFTESTHTMLJAVASERVICE = "InternalPerfTestHTMLJavaServiceImpl";

    String DEFAULTHTMLJAVASERVICE = "defaultHtmlJavaServiceImpl";
    /**
     * HTMLJAVASERVICE
     */
    String HAWKEXECUTIONCOMMANDFACTORY = "hawkExecutionCommandFactory";
    /**
     *
     */
    String LOGGINGCONFIG = "logginConfig";
    /**
     *
     */
    String HAWKMODULECONFIG = "hawkModuleConfig";
    /**
     *
     */
    String SHUTDOWNHOOKCONFIG = "shutdownHookConfig";
    
    
    /**
     *
     */
    String PLUGINDEPOYER = "pluginDeployer";
    /**
     *
     */
    String SYSTEMMODULE = "systemModule";
    /**
     *
     */
    String MATHMODULE = "mathModule";
    /**
     *
     */
    String HTTPMODULE = "httpModule";
    /**
     *
     */
    String FILEMODULE = "fileModule";
    /**
     *
     */
    String TESTMODULE = "testModule";
    /**
     *
     */
    String SCRIPTCOMPILATIONCOMMAND = "scriptCompilationCommand";
    /**
     *
     */
    String SCRIPTINTERPRRETATIONPERFCOMMAND = "scriptInterpretationPerfCommand";
    /**
     *
     */
    String SCRIPTINTERPRETATIONDEBUGCOMMAND = "scriptInterpretationDebugCommand";
    /**
     *
     */
    String TRAININGCOMMAND = "trainingCommand";
    /**
     *
     */
    String VERSIONCOMMAND = "versionCommand";

    String DEFAULTHTMLFORMATTER = "defaultHTMLFormatter";
    /**
     *
     */
    String SCRIPTHTMLCOMMAND = "scriptHTMLCommand";
    /**
     *
     */
    String INTERNALFUNCTIONALTESTCOMMAND = "internalFunctionalTestCommand";
    /**
     *
     */
    String INTERNALPERFTESTCOMMAND = "internalPerfTestCommand";
    /**
     *
     */
    String GUICOMMAND = "guiCommand";
    /**
     *
     */
    String HELPCOMMAND = "helpCommand";
    String TEMPLATESERVICEIMPL = "templateServiceImpl";
    String FILETEMPLATEJAVABEAN = "fileTemplateJavaBean";
    String HAWKPLUGINCALLBACKREGISTRY = "hawkPluginCallbackRegistry";
    String POSTPLUGINDEPLOYMENTEVENT = "postPluginDeploymentEvent";
    String PLUGINVALIDATIONEVENT = "pluginValidationEvent";
    String PLUGINCOPYEVENT = "pluginCopyEvent";
    String PLUGINEXTRACTIONEVENT = "pluginExtractionEvent";
    String HAWKOUTPUTEVENT = "hawkOutputEvent";
    String HAWKERROREVENT = "hawkErrorEvent";
    String HAWKIOEVENTCALLBACK = "hawkIOEventCallback";
    String PLUGINLOADINGEVENT = "pluginLoadingEvent";
    String POSTPLUGINUNDEPLOYMENTEVENT = "postPluginUnDeploymentEvent";
    String PREPLUGINDEPLOYMENTEVENT = "prePluginDeploymentEvent";
    String PREPLUGINUNDEPLOYMENTEVENT = "prePluginUnDeploymentEvent";
    String DEFAULTPLUGINEVENT = "defaultPluginEvent";
    String HAWKPLUGINSERVICE = "hawkPluginServiceImpl";
    String PLUGINEVENTEXECUTOR = "pluginEventExecutor";
    String PLUGINDEPLOYMENTEXECUTOR = "pluginDeploymentExecutor";
    String PLUGINUNDEPLOYMENTEXECUTOR = "pluginUnDeploymentExecutor";
    String PLUGINDEPLOYMENTCOMMAND = "pluginDeploymentCommand";
    String PLUGINUNDEPLOYMENTCOMMAND = "pluginUnDeploymentCommand";
    String SHOWINSTALLEDPLUGINCOMMAND = "showInstalledPluginCommand";
    String SHOWDOWNLOADEDPLUGINCOMMAND = "showDownloadedPluginCommand";
    String SHOWAVAILABLEPLUGINCOMMAND = "showAvailablePluginCommand";
    String SHOWDOWNLOADEDPLUGINEXECUTOR = "showDownloadedPluginExecutor";
    String SHOWINSTALLEDPLUGINEXECUTOR = "showInstalledPluginExecutor";
    String SHOWAVAILABLEPLUGINEXECUTOR = "showAvailablePluginExecutor";
    String SHOWNYDPLUGINCOMMAND = "showNYDPluginCommand";
    String SHOWNYDPLUGINEXECUTOR = "showNYDPluginExecutor";
    String SHOWNYIPLUGINCOMMAND = "showNYIPluginCommand";
    String SHOWNYIPLUGINEXECUTOR = "showNYIPluginExecutor";
    String DEFAULTPLUGINEXECUTOR = "defaultPluginExecutor";
    String ALLPLUGINUNDEPLOYMENTCOMMAND = "allPluginUnDeploymentCommand";
    String ALLPLUGINUNDEPLOYMENTEXECUTOR = "allPluginUnDeploymentExecutor";
    String SHOWPLUGINDETAILSCOMMAND = "showPluginDetailsCommand";
    String SHOWPLUGINDETAILSEXECUTOR = "showPluginDetailsExecutor";
    String SHOWPLUGINAUTHORCOMMAND = "showPluginAuthorCommand";
    String SHOWPLUGINAUTHOREXECUTOR = "showPluginAuthorExecutor";
    String SHOWPLUGINVERSIONCOMMAND = "showPluginVersionCommand";
    String SHOWPLUGINVERSIONEXECUTOR = "showPluginVersionExecutor";

    String SCRIPTCACHEPROVIDER = "scriptCacheProvider";

    /**
     * DEFAULTSEQUENCEPROVIDER
     */
    String FUNCTIONEXECUTOR = "functionExecutor";
}
