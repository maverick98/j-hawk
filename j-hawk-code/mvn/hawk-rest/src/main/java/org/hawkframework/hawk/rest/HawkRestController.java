/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawkframework.hawk.rest;

/**
 *
 * @author reemeeka
 */


import java.io.File;
import org.common.di.AppContainer;
import org.commons.file.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.hawk.executor.command.interpreter.ScriptInterpretationCommand;
import org.hawk.output.DefaultHawkOutputWriter;
import org.hawk.output.HawkOutput;

@RestController
public class HawkRestController {
	
	
    @GetMapping("/version")
    public String version(){
        return "21.04";
    }
    @PostMapping("/run")
    public String run(@RequestBody String hawkCode) throws Exception
    {
            HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
            DefaultHawkOutputWriter hawkOutputWriter = (DefaultHawkOutputWriter)hawkOutput.getHawkOutputWriter();
            File echoFile = hawkOutputWriter.getEchoFile();
            echoFile.delete();
            echoFile = File.createTempFile("echo", ".output");
            hawkOutputWriter.setEchoFile(echoFile);
            hawkOutput.setHawkOutputWriter(hawkOutputWriter);
           String output = null;
           ScriptInterpretationCommand command = new ScriptInterpretationCommand();
           command.setHawkScriptData(hawkCode);
           command.execute();
           //HawkOutput hawkOutput = AppContainer.getInstance().getBean(ScriptExecutor.class).getHawkOutput();
           output = FileUtil.readFile(echoFile);
           
           return output;

    }
   
	
	
}
