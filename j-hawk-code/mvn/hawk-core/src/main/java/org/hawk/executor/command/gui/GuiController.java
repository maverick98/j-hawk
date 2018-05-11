package org.hawk.executor.command.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.common.di.AppContainer;
import org.fxmisc.richtext.CodeArea;
import org.hawk.executor.command.interpreter.ScriptInterpretationDebugCommand;
import org.hawk.executor.command.interpreter.ScriptInterpretationPerfCommand;
import org.hawk.output.HawkOutput;

/**
 * FXML Controller class
 *
 * @author reemeeka
 */
public class GuiController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // First we deal with the FXML
    // dependency injection
    @FXML
    private MenuItem openMI;

    @FXML
    private MenuItem saveMI;

    @FXML
    private MenuItem saveAsMI;

    @FXML
    private MenuItem quitMI;

    @FXML
    private MenuItem runHawkMI;

    @FXML
    private CodeArea codeArea;
    @FXML
    private TextArea outputArea;
    
    private AboutJHawk helpJHawk = new AboutJHawk();
    
     private PowerToolStage powerTool = new PowerToolStage();

    @FXML
    public void openAction(ActionEvent event) {
        handleOpenClick();
    }

    @FXML
    public void saveAction(ActionEvent event) {


        handleSaveClick();
    }

    @FXML
    public void saveAsAction(ActionEvent event) {
        handleSaveAsClick();
    }

    @FXML
    public void aboutHawkAction(ActionEvent event) {
        helpJHawk.show();
    }
    @FXML
    public void runHawkAction(ActionEvent event) {
        try {

            GUIHawkOutputWriter gUIHawkOutputWriter = new GUIHawkOutputWriter(outputArea);
            gUIHawkOutputWriter.clear();
            ScriptInterpretationPerfCommand scriptInterpretationCommand = AppContainer.getInstance().getBean(ScriptInterpretationPerfCommand.class);
            scriptInterpretationCommand.setHawkScriptData(codeArea.getText());
            HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
            hawkOutput.setHawkOutputWriter(gUIHawkOutputWriter);
            scriptInterpretationCommand.execute();
        } catch (Exception ex) {
            Logger.getLogger(GUIHawkMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void debugHawkAction(ActionEvent event) {
        try {

            GUIHawkOutputWriter gUIHawkOutputWriter = new GUIHawkOutputWriter(outputArea);
            gUIHawkOutputWriter.clear();
            ScriptInterpretationDebugCommand scriptInterpretationCommand = AppContainer.getInstance().getBean(ScriptInterpretationDebugCommand.class);
            scriptInterpretationCommand.setHawkScriptData(codeArea.getText());
            HawkOutput hawkOutput = AppContainer.getInstance().getBean(HawkOutput.class);
            hawkOutput.setHawkOutputWriter(gUIHawkOutputWriter);
            scriptInterpretationCommand.execute();
        } catch (Exception ex) {
            Logger.getLogger(GUIHawkMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    public void toolsAction(ActionEvent event) {

        powerTool.show();
    }

    @FXML
    void quitAction(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void initialize() {
    }

 
    private File dataFile = null;

    private void handleOpenClick() {

        FileChooser fc = new FileChooser();
        fc.setTitle("Get Hawk Script");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Hawk Files", "*.hawk"), new ExtensionFilter("All Files", "*.*"));
        File phil = fc.showOpenDialog(GUIHawkMain.primaryStage);
        if (phil != null) {
            try (Scanner scan = new Scanner(phil)) {
                String content = scan.useDelimiter("\\Z").next();
                codeArea.replaceText(content);
                dataFile = phil;
                saveMI.setDisable(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSaveAsClick() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Hawk Script");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Hawk Files", "*.hawk"), new ExtensionFilter("All Files", "*.*"));
        File phil = fc.showOpenDialog(GUIHawkMain.primaryStage);
        if (phil != null) {
            try (PrintStream ps = new PrintStream(phil)) {
                ps.print(codeArea.getText());
                dataFile = phil;
                saveMI.setDisable(false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSaveClick() {
        try (PrintStream ps= new PrintStream(dataFile)) {
            ps.print(codeArea.getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    

}
