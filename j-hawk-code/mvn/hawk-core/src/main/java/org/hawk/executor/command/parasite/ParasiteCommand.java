/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.

 * 
 *
 * 
 */
package org.hawk.executor.command.parasite;

import java.util.Map;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ParasiteCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-parasite\\s*-cs\\s*=\\s*([a-z|A-Z|\\.]*)\\s*-f\\s*(.*)\\s*";
    private static final String USAGE = "-parasite -cs = \"{startup  of container where hawk is residing}\" -f {hawk script file}";

    private String containerStartupClassStr;
    private String hawkFile;

    public String getHawkFile() {
        return hawkFile;
    }

    public void setHawkFile(String hawkFile) {
        this.hawkFile = hawkFile;
    }
    
    public String getContainerStartupClassStr() {
        return containerStartupClassStr;
    }

    public void setContainerStartupClassStr(String containerStartupClassStr) {
        this.containerStartupClassStr = containerStartupClassStr;
    }

    @Override
    public String getRegEx() {
        return super.getRegEx(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public ParasiteCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

     @Override
    public boolean onCommandFound(Map<Integer, String> map) {
        String clazzStr = map.get(1);
        this.setContainerStartupClassStr(clazzStr);
        String hawkFileStr = map.get(2);
        this.setHawkFile(hawkFileStr);
        return true;
    }
    @Override
    public boolean execute() throws Exception {
        AppContainer.getInstance().getBean(ParasiteExecutor.class).execute(this);
        return true;
    }
     @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(ParasiteCommand.class);
    }
}
