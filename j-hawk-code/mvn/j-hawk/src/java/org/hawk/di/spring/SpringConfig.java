/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.hawk.di.spring;

import org.hawk.executor.command.parasite.ParasiteCommand;
import org.hawk.executor.command.parasite.ParasiteExecutor;
import org.hawk.module.core.cache.CoreModuleCache;
import org.hawk.module.cache.AllModuleCache;
import org.hawk.module.cache.ModuleCacheSequenceProvider;
import org.hawk.module.container.cache.ContainerModuleCache;
import org.hawk.module.plugin.cache.PluginModuleCache;
import org.hawk.module.task.SubTaskCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class SpringConfig {

    @Bean
    public org.hawk.executor.command.plugin.details.ShowPluginDetailsCommand getShowPluginDetailsCommand() {
        return new org.hawk.executor.command.plugin.details.ShowPluginDetailsCommand();
    }

    @Bean
    public org.hawk.tst.HawkTestProperties getHawkTestProperties() {
        return new org.hawk.tst.HawkTestProperties();
    }

    @Bean
    public org.hawk.module.core.FileModule getFileModule() {
        return new org.hawk.module.core.FileModule();
    }

    @Bean
    public org.hawk.lang.multiline.MultiLineScriptVerticalSequenceProvider getMultiLineScriptVerticalSequenceProvider() {
        return new org.hawk.lang.multiline.MultiLineScriptVerticalSequenceProvider();
    }

    @Bean
    public org.hawk.template.FileTemplateJavaBean getFileTemplateJavaBean() {
        return new org.hawk.template.FileTemplateJavaBean();
    }

    @Bean
    public org.hawk.executor.command.HawkCommandParser getHawkCommandParser() {
        return new org.hawk.executor.command.HawkCommandParser();
    }

    @Bean
    public org.hawk.executor.command.plugin.author.ShowPluginAuthorCommand getShowPluginAuthorCommand() {
        return new org.hawk.executor.command.plugin.author.ShowPluginAuthorCommand();
    }

    @Bean
    public org.hawk.config.HawkModuleConfig getHawkModuleConfig() {
        return new org.hawk.config.HawkModuleConfig();
    }

    @Bean
    public org.hawk.executor.command.compiler.ScriptCompiler getScriptCompiler() {
        return new org.hawk.executor.command.compiler.ScriptCompiler();
    }

    @Bean
    public org.hawk.executor.command.homepage.ScriptHTMLCommand getScriptHTMLCommand() {
        return new org.hawk.executor.command.homepage.ScriptHTMLCommand();
    }

    @Bean
    public org.hawk.module.core.HttpModule getHttpModule() {
        return new org.hawk.module.core.HttpModule();
    }
    
    @Bean
    public org.hawk.executor.ModuleExecutor getModuleExecutor() {
        return new org.hawk.executor.ModuleExecutor();
    }

    @Bean
    public org.hawk.plugin.event.HawkPluginExtractionEvent getHawkPluginExtractionEvent() {
        return new org.hawk.plugin.event.HawkPluginExtractionEvent();
    }

    @Bean
    public org.hawk.executor.command.help.HelpCommand getHelpCommand() {
        return new org.hawk.executor.command.help.HelpCommand();
    }

    @Bean
    public org.hawk.executor.command.plugin.deploy.PluginDeploymentCommand getPluginDeploymentCommand() {
        return new org.hawk.executor.command.plugin.deploy.PluginDeploymentCommand();
    }

    @Bean
    public org.commons.event.callback.HawkEventCallbackRegistry getHawkEventCallbackRegistry() {
        return new org.commons.event.callback.HawkEventCallbackRegistry();
    }

    @Bean
    public org.hawk.config.HawkConfigSequenceProvider getHawkConfigSequenceProvider() {
        return new org.hawk.config.HawkConfigSequenceProvider();
    }

    @Bean
    public org.hawk.executor.command.plugin.downloaded.ShowDownloadedPluginCommand getShowDownloadedPluginCommand() {
        return new org.hawk.executor.command.plugin.downloaded.ShowDownloadedPluginCommand();
    }

    @Bean
    public org.hawk.executor.cache.multiline.function.FunctionScriptCache getFunctionScriptCache() {
        return new org.hawk.executor.cache.multiline.function.FunctionScriptCache();
    }

    @Bean
    public org.hawk.executor.command.plugin.undeploy.AllPluginUnDeploymentCommand getAllPluginUnDeploymentCommand() {
        return new org.hawk.executor.command.plugin.undeploy.AllPluginUnDeploymentCommand();
    }

    @Bean
    public org.hawk.executor.cache.DefaultScriptCache getDefaultScriptCache() {
        return new org.hawk.executor.cache.DefaultScriptCache();
    }

    @Bean
    public org.hawk.data.perf.PerfDataProcessor getPerfDataProcessor() {
        return new org.hawk.data.perf.PerfDataProcessor();
    }

    @Bean
    public org.hawk.plugin.event.PreHawkPluginDeploymentEvent getPreHawkPluginDeploymentEvent() {
        return new org.hawk.plugin.event.PreHawkPluginDeploymentEvent();
    }

    @Bean
    public org.hawk.executor.command.plugin.installed.ShowInstalledPluginCommand getShowInstalledPluginCommand() {
        return new org.hawk.executor.command.plugin.installed.ShowInstalledPluginCommand();
    }

    @Bean
    public org.hawk.executor.command.plugin.undeploy.PluginUnDeploymentExecutor getPluginUnDeploymentExecutor() {
        return new org.hawk.executor.command.plugin.undeploy.PluginUnDeploymentExecutor();
    }

    @Bean
    public org.hawk.executor.command.plugin.nyi.ShowNYIPluginCommand getShowNYIPluginCommand() {
        return new org.hawk.executor.command.plugin.nyi.ShowNYIPluginCommand();
    }

    @Bean
    public org.hawk.executor.cache.multiline.multilinecomment.MultiLineCommentScriptCache getMultiLineCommentScriptCache() {
        return new org.hawk.executor.cache.multiline.multilinecomment.MultiLineCommentScriptCache();
    }

    @Bean
    public org.hawk.main.HawkTargetSetting getHawkTargetSetting() {
        return new org.hawk.main.HawkTargetSetting();
    }

    @Bean
    public org.hawk.output.event.callback.HawkIOEventCallback getHawkIOEventCallback() {
        return new org.hawk.output.event.callback.HawkIOEventCallback();
    }

    @Bean
    public org.hawk.executor.cache.multiline.MultiLineScriptCache getMultiLineScriptCache() {
        return new org.hawk.executor.cache.multiline.MultiLineScriptCache();
    }

    @Bean
    public org.hawk.output.HawkOutput getHawkOutput() {
        return new org.hawk.output.HawkOutput();
    }

    @Bean
    public org.hawk.executor.cache.multiline.function.GlobalFunctionTemplateScriptCache getGlobalFunctionTemplateScriptCache() {
        return new org.hawk.executor.cache.multiline.function.GlobalFunctionTemplateScriptCache();
    }

    @Bean
    public org.hawk.software.SoftwareServiceImpl getSoftwareServiceImpl() {
        return new org.hawk.software.SoftwareServiceImpl();
    }

    @Bean
    public org.hawk.executor.command.HawkExecutionCommandFactory getHawkExecutionCommandFactory() {
        return new org.hawk.executor.command.HawkExecutionCommandFactory();
    }

    @Bean
    public org.hawk.executor.cache.ScriptCachingSequenceProvider getScriptCachingSequenceProvider() {
        return new org.hawk.executor.cache.ScriptCachingSequenceProvider();
    }

    @Bean
    public org.hawk.plugin.PluginDeployerImpl getPluginDeployerImpl() {
        return new org.hawk.plugin.PluginDeployerImpl();
    }

    @Bean
    public org.hawk.executor.command.help.HelpExecutor getHelpExecutor() {
        return new org.hawk.executor.command.help.HelpExecutor();
    }

    @Bean
    public org.hawk.executor.command.compiler.ScriptCompilationCommand getScriptCompilationCommand() {
        return new org.hawk.executor.command.compiler.ScriptCompilationCommand();
    }

    @Bean
    public org.hawk.executor.command.gui.GUIExecutor getGUIExecutor() {
        return new org.hawk.executor.command.gui.GUIExecutor();
    }

    @Bean
    public org.hawk.template.TemplateServiceImpl getTemplateServiceImpl() {
        return new org.hawk.template.TemplateServiceImpl();
    }

    @Bean
    public org.hawk.html.DefaultHtmlFormatter getDefaultHtmlFormatter() {
        return new org.hawk.html.DefaultHtmlFormatter();
    }

    @Bean
    public org.hawk.executor.command.interpreter.ScriptExecutor getScriptExecutor() {
        return new org.hawk.executor.command.interpreter.ScriptExecutor();
    }

    @Bean
    public org.hawk.executor.command.internaltest.perf.InternalPerfTestExecutor getInternalPerfTestExecutor() {
        return new org.hawk.executor.command.internaltest.perf.InternalPerfTestExecutor();
    }

    @Bean
    public org.hawk.sequence.DefaultSequenceProvider getDefaultSequenceProvider() {
        return new org.hawk.sequence.DefaultSequenceProvider();
    }

    @Bean
    public org.hawk.module.core.SystemModule getSystemModule() {
        return new org.hawk.module.core.SystemModule();
    }

    @Bean
    public org.hawk.tst.perf.InternalPerfTestHTMLJavaServiceImpl getInternalPerfTestHTMLJavaServiceImpl() {
        return new org.hawk.tst.perf.InternalPerfTestHTMLJavaServiceImpl();
    }

    @Bean
    public org.hawk.plugin.event.PostHawkPluginDeploymentEvent getPostHawkPluginDeploymentEvent() {
        return new org.hawk.plugin.event.PostHawkPluginDeploymentEvent();
    }

    @Bean
    public org.hawk.executor.cache.singleline.alias.AliasScriptCache getAliasScriptCache() {
        return new org.hawk.executor.cache.singleline.alias.AliasScriptCache();
    }

    @Bean
    public org.hawk.executor.command.training.TrainingCommand getTrainingCommand() {
        return new org.hawk.executor.command.training.TrainingCommand();
    }

    @Bean
    public org.hawk.executor.cache.singleline.xmlstruct.XMLStructScriptCache getXMLStructScriptCache() {
        return new org.hawk.executor.cache.singleline.xmlstruct.XMLStructScriptCache();
    }

    @Bean
    public org.hawk.output.event.HawkOutputEvent getHawkOutputEvent() {
        return new org.hawk.output.event.HawkOutputEvent();
    }

    @Bean
    public org.hawk.plugin.event.PostHawkPluginUndeploymentEvent getPostHawkPluginUndeploymentEvent() {
        return new org.hawk.plugin.event.PostHawkPluginUndeploymentEvent();
    }

    @Bean
    public org.hawk.executor.command.plugin.undeploy.AllPluginUnDeploymentExecutor getAllPluginUnDeploymentExecutor() {
        return new org.hawk.executor.command.plugin.undeploy.AllPluginUnDeploymentExecutor();
    }

    @Bean
    public org.hawk.input.HawkInput getHawkInput() {
        return new org.hawk.input.HawkInput();
    }

    @Bean
    public org.hawk.plugin.event.DefaultHawkPluginEvent getDefaultHawkPluginEvent() {
        return new org.hawk.plugin.event.DefaultHawkPluginEvent();
    }

    @Bean
    public org.hawk.html.java.DefaultHtmlJavaServiceImpl getDefaultHtmlJavaServiceImpl() {
        return new org.hawk.html.java.DefaultHtmlJavaServiceImpl();
    }

    @Bean
    public org.hawk.executor.command.plugin.installed.ShowInstalledPluginExecutor getShowInstalledPluginExecutor() {
        return new org.hawk.executor.command.plugin.installed.ShowInstalledPluginExecutor();
    }

    @Bean
    public org.hawk.executor.command.gui.GUICommand getGUICommand() {
        return new org.hawk.executor.command.gui.GUICommand();
    }

    @Bean
    public org.hawk.executor.command.plugin.DefaultPluginExecutor getDefaultPluginExecutor() {
        return new org.hawk.executor.command.plugin.DefaultPluginExecutor();
    }

    @Bean
    public org.hawk.executor.command.plugin.version.ShowPluginVersionCommand getShowPluginVersionCommand() {
        return new org.hawk.executor.command.plugin.version.ShowPluginVersionCommand();
    }

    @Bean
    public org.hawk.executor.command.plugin.undeploy.PluginUnDeploymentCommand getPluginUnDeploymentCommand() {
        return new org.hawk.executor.command.plugin.undeploy.PluginUnDeploymentCommand();
    }

    @Bean
    public org.hawk.plugin.event.HawkPluginValidationEvent getHawkPluginValidationEvent() {
        return new org.hawk.plugin.event.HawkPluginValidationEvent();
    }

    @Bean
    public org.hawk.plugin.HawkPluginServiceImpl getHawkPluginServiceImpl() {
        return new org.hawk.plugin.HawkPluginServiceImpl();
    }

    @Bean
    public org.hawk.module.core.ExcelModule getExcelModule() {
        return new org.hawk.module.core.ExcelModule();
    }

    @Bean
    public org.hawk.executor.command.interpreter.ScriptInterpretationPerfCommand getScriptInterpretationPerfCommand() {
        return new org.hawk.executor.command.interpreter.ScriptInterpretationPerfCommand();
    }

    @Bean
    public org.hawk.module.properties.HawkModulePropertiesManager getHawkModulePropertiesManager() {
        return new org.hawk.module.properties.HawkModulePropertiesManager();
    }

    @Bean
    public org.hawk.executor.command.plugin.available.ShowAvailablePluginCommand getShowAvailablePluginCommand() {
        return new org.hawk.executor.command.plugin.available.ShowAvailablePluginCommand();
    }

    @Bean
    public org.hawk.executor.command.plugin.nyd.ShowNYDPluginCommand getShowNYDPluginCommand() {
        return new org.hawk.executor.command.plugin.nyd.ShowNYDPluginCommand();
    }

    @Bean
    public org.hawk.executor.command.internaltest.perf.InternalPerfTestCommand getInternalPerfTestCommand() {
        return new org.hawk.executor.command.internaltest.perf.InternalPerfTestCommand();
    }

    @Bean
    public org.hawk.executor.command.plot.PlottingExecutor getPlottingExecutor() {
        return new org.hawk.executor.command.plot.PlottingExecutor();
    }

    @Bean
    public org.hawk.executor.command.plugin.available.ShowAvailablePluginExecutor getShowAvailablePluginExecutor() {
        return new org.hawk.executor.command.plugin.available.ShowAvailablePluginExecutor();
    }

    @Bean
    public org.hawk.lang.function.FunctionExecutor getFunctionExecutor() {
        return new org.hawk.lang.function.FunctionExecutor();
    }

    @Bean
    public org.hawk.executor.command.plugin.nyi.ShowNYIPluginExecutor getShowNYIPluginExecutor() {
        return new org.hawk.executor.command.plugin.nyi.ShowNYIPluginExecutor();
    }

    @Bean
    public org.hawk.tst.functional.html.InternalFuncTestHTMLJavaServiceImpl getInternalFuncTestHTMLJavaServiceImpl() {
        return new org.hawk.tst.functional.html.InternalFuncTestHTMLJavaServiceImpl();
    }

    @Bean
    public org.hawk.executor.command.plugin.deploy.PluginDeploymentExecutor getPluginDeploymentExecutor() {
        return new org.hawk.executor.command.plugin.deploy.PluginDeploymentExecutor();
    }

    @Bean
    public org.hawk.executor.command.version.VersionCommand getVersionCommand() {
        return new org.hawk.executor.command.version.VersionCommand();
    }

    @Bean
    public org.hawk.module.core.MathModule getMathModule() {
        return new org.hawk.module.core.MathModule();
    }

    @Bean
    public org.hawk.executor.command.homepage.ScriptHTMLExecutor getScriptHTMLExecutor() {
        return new org.hawk.executor.command.homepage.ScriptHTMLExecutor();
    }

    @Bean
    public org.hawk.executor.cache.singleline.SingleLineScriptCache getSingleLineScriptCache() {
        return new org.hawk.executor.cache.singleline.SingleLineScriptCache();
    }

    @Bean
    public org.hawk.executor.command.plugin.author.ShowPluginAuthorExecutor getShowPluginAuthorExecutor() {
        return new org.hawk.executor.command.plugin.author.ShowPluginAuthorExecutor();
    }

    @Bean
    public org.hawk.executor.command.internaltest.functional.InternalFunctionalTestExecutor getInternalFunctionalTestExecutor() {
        return new org.hawk.executor.command.internaltest.functional.InternalFunctionalTestExecutor();
    }

    @Bean
    public org.hawk.executor.command.plugin.downloaded.ShowDownloadedPluginExecutor getShowDownloadedPluginExecutor() {
        return new org.hawk.executor.command.plugin.downloaded.ShowDownloadedPluginExecutor();
    }

    @Bean
    public org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache getGlobalVariableScriptCache() {
        return new org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache();
    }

    @Bean
    public org.hawk.config.logging.LoggingConfig getLoggingConfig() {
        return new org.hawk.config.logging.LoggingConfig();
    }

    @Bean
    public org.hawk.output.event.HawkErrorEvent getHawkErrorEvent() {
        return new org.hawk.output.event.HawkErrorEvent();
    }

    @Bean
    public org.hawk.executor.command.interpreter.ScriptInterpretationDebugCommand getScriptInterpretationDebugCommand() {
        return new org.hawk.executor.command.interpreter.ScriptInterpretationDebugCommand();
    }

    @Bean
    public org.hawk.executor.DefaultScriptExecutor getDefaultScriptExecutor() {
        return new org.hawk.executor.DefaultScriptExecutor();
    }

    @Bean
    public org.hawk.executor.command.plugin.nyd.ShowNYDPluginExecutor getShowNYDPluginExecutor() {
        return new org.hawk.executor.command.plugin.nyd.ShowNYDPluginExecutor();
    }

    @Bean
    public org.hawk.config.shutdown.ShutdownHookConfig getShutdownHookConfig() {
        return new org.hawk.config.shutdown.ShutdownHookConfig();
    }

    @Bean
    public org.hawk.module.core.TestModule getTestModule() {
        return new org.hawk.module.core.TestModule();
    }

    @Bean
    public org.hawk.lang.type.IntegerOverflow getIntegerOverflow() {
        return new org.hawk.lang.type.IntegerOverflow();
    }

    @Bean
    public org.hawk.executor.command.training.TrainingExecutor getTrainingExecutor() {
        return new org.hawk.executor.command.training.TrainingExecutor();
    }

    @Bean
    public org.hawk.executor.command.internaltest.functional.InternalFunctionalTestCommand getInternalFunctionalTestCommand() {
        return new org.hawk.executor.command.internaltest.functional.InternalFunctionalTestCommand();
    }

    @Bean
    public org.hawk.executor.cache.ScriptCacheProvider getScriptCacheProvider() {
        return new org.hawk.executor.cache.ScriptCacheProvider();
    }

    @Bean
    public org.hawk.plugin.event.HawkPluginLoadingEvent getHawkPluginLoadingEvent() {
        return new org.hawk.plugin.event.HawkPluginLoadingEvent();
    }

    @Bean
    public org.hawk.executor.command.plugin.details.ShowPluginDetailsExecutor getShowPluginDetailsExecutor() {
        return new org.hawk.executor.command.plugin.details.ShowPluginDetailsExecutor();
    }

    @Bean
    public org.hawk.plugin.event.PreHawkPluginUndeploymentEvent getPreHawkPluginUndeploymentEvent() {
        return new org.hawk.plugin.event.PreHawkPluginUndeploymentEvent();
    }

    @Bean
    public org.hawk.executor.cache.multiline.structure.StructureDefinitionScriptCache getStructureDefinitionScriptCache() {
        return new org.hawk.executor.cache.multiline.structure.StructureDefinitionScriptCache();
    }

    @Bean
    public org.hawk.executor.command.plugin.version.ShowPluginVersionExecutor getShowPluginVersionExecutor() {
        return new org.hawk.executor.command.plugin.version.ShowPluginVersionExecutor();
    }

    @Bean
    public org.hawk.executor.command.version.VersionExecutor getVersionExecutor() {
        return new org.hawk.executor.command.version.VersionExecutor();
    }

    @Bean
    public ParasiteCommand parasiteCommand() {
        return new ParasiteCommand();
    }

    @Bean
    public ParasiteExecutor parasiteExecutor() {
        return new ParasiteExecutor();
    }

    @Bean
    public SubTaskCache subTaskCache() {
        return new SubTaskCache();
    }

    @Bean
    public SubTaskCache coreSubTaskCache() {
        return new SubTaskCache();
    }

    @Bean
    public SubTaskCache pluginSubTaskCache() {
        return new SubTaskCache();
    }

    @Bean
    public SubTaskCache containerSubTaskCache() {
        return new SubTaskCache();
    }

    @Bean
    public CoreModuleCache coreModuleCache() {
        return new CoreModuleCache();
    }

    @Bean
    public PluginModuleCache pluginModuleCache() {
        return new PluginModuleCache();
    }

    @Bean
    public ContainerModuleCache containerModuleCache() {
        return new ContainerModuleCache();
    }

    @Bean
    public AllModuleCache moduleCache() {
        return new AllModuleCache();
    }

    @Bean
    public ModuleCacheSequenceProvider moduleCacheSequenceProvider() {
        return new ModuleCacheSequenceProvider();
    }

    /*
     @Bean
     public org.hawk.executor.cache.multiline.function.LocalFunctionTemplateScriptCache getLocalFunctionTemplateScriptCache() {
     return new org.hawk.executor.cache.multiline.function.LocalFunctionTemplateScriptCache();
     }
     */
}
