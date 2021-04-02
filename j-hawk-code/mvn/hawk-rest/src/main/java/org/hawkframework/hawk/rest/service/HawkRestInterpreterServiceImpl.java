/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawkframework.hawk.rest.service;

import java.io.File;
import org.commons.file.FileUtil;
import org.hawk.executor.command.compiler.ScriptCompilationCommand;
import org.hawk.executor.command.interpreter.ScriptInterpretationCommand;
import org.hawkframework.hawk.rest.util.HawkRestConfigHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author reemeeka
 */
@Service
public class HawkRestInterpreterServiceImpl implements IHawkRestInterpreterService {

    @Override
    public String interpret(String hawkCode) throws Exception{
        File echoFile = HawkRestConfigHelper.setJHawkOutput("echo", "output");
        String output;
        ScriptInterpretationCommand command = new ScriptInterpretationCommand();
        command.setHawkScriptData(hawkCode);
        command.execute();
      
        output = FileUtil.readFile(echoFile);
        echoFile.delete();
        return output;
    }
    @Override
    public String compile(String hawkCode) throws Exception{
        File echoFile = HawkRestConfigHelper.setJHawkOutput("echo", "output");
        String output;
        ScriptCompilationCommand command = new ScriptCompilationCommand();
        command.setHawkScriptData(hawkCode);
        command.execute();
      
        output = FileUtil.readFile(echoFile);
        echoFile.delete();
        return output;
    }

}
