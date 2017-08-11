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
package org.hawk.executor.command;


import org.springframework.beans.factory.annotation.Autowired;

/**
 * Defines the various modes which helps hawk script developer
 *
 * @version 1.0 9 Apr, 2010
 * @author msahu
 * @see BuildModeEnum
 */
   
   
public class HawkCommandParser {

    private IHawkExecutionCommand hawkExecutionCommand;
    @Autowired(required = true)
       
    private IHawkExecutionCommandFactory hawkExecutionCommandFactory;

    public IHawkExecutionCommandFactory getHawkExecutionCommandFactory() {
        return hawkExecutionCommandFactory;
    }

    public void setHawkExecutionCommandFactory(IHawkExecutionCommandFactory hawkExecutionCommandFactory) {
        this.hawkExecutionCommandFactory = hawkExecutionCommandFactory;
    }

    public IHawkExecutionCommand getHawkExecutionCommand() {
        return hawkExecutionCommand;
    }

    public void setHawkExecutionCommand(IHawkExecutionCommand hawkExecutionCommand) {
        this.hawkExecutionCommand = hawkExecutionCommand;
    }

    /**
     * Parses the input arguments to find the mode <tt>BuildModeEnum</tt>
     *
     * @param args array conataing user options
     * @return <tt>true</tt> on success <tt>false</tt> on failure
     * @throws org.hawk.exception.Exception
     */
    public boolean parseArguments(String args[]) throws Exception {

        this.hawkExecutionCommand = this.getHawkExecutionCommandFactory().create(args);
        return this.hawkExecutionCommand != null;
    }

    public boolean shouldCollectPerfData() {

        boolean result = false;
        if (this.getHawkExecutionCommand() != null) {
            result = this.getHawkExecutionCommand().shouldCollectPerfData();
        }
        return result;
    }

    public boolean shouldCacheHawkCoreModulesOnly() {



        boolean result = false;
        if (this.getHawkExecutionCommand() != null) {
            result = this.getHawkExecutionCommand().shouldCacheHawkCoreModulesOnly();
        }
        return result;

    }

    public boolean shouldCacheConfig() {


        boolean result = false;
        if (this.getHawkExecutionCommand() != null) {
            result = this.getHawkExecutionCommand().shouldCacheConfig();
        }
        return result;
    }

    public boolean shouldDumpModuleExecution() {

        boolean result = false;
        if (this.getHawkExecutionCommand() != null) {
            result = this.getHawkExecutionCommand().shouldDumpModuleExecution();
        }
        return result;
    }

    public boolean shouldUseLogger() {
        //return !(this.getBuildMode() == null || this.getBuildMode() == HawkExecutionCommandEnum.SCRIPTING);
        boolean result = false;
        if (this.getHawkExecutionCommand() != null) {
            result = this.getHawkExecutionCommand().shouldUseLogger();
        }
        return result;
    }
}
