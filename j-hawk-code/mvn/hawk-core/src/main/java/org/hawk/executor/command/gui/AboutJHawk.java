/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.executor.command.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.common.di.AppContainer;
import org.hawk.software.ISoftwareService;
import org.hawk.software.Software;
import org.hawk.software.SoftwareServiceImpl;

/**
 *
 * @author reemeeka
 */
    public class AboutJHawk extends Stage {

    public AboutJHawk() {
        super();
        this.setTitle("About j-hawk");
        this.setResizable(false);

        this.initModality(Modality.APPLICATION_MODAL);

        BorderPane borderPaneOptionPane = new BorderPane();

        Label label = new Label();

        label.setText(formatAbout());

        borderPaneOptionPane.setCenter(label); // For example

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            this.hide();
        });

        borderPaneOptionPane.setPadding(new Insets(5));

        Scene s = new Scene(borderPaneOptionPane);
        this.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logo.png")));
    
        this.setScene(s);
    }

    private String formatAbout() {
        StringBuilder sb = new StringBuilder();
        ISoftwareService softwareService = AppContainer.getInstance().getBean(SoftwareServiceImpl.class);
        Software software = null;
        try {
            software = softwareService.getSoftware();
        } catch (Exception ex) {
            Logger.getLogger(AboutJHawk.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

        sb.append("\n");
        sb.append("Author");
        sb.append(" : ");
        sb.append(software.getContributor().getName());
        sb.append("\n");
        sb.append("Email");
        sb.append(" : ");
        sb.append(software.getContributor().getEmail());
        sb.append("\n");
        sb.append("Release Date");
        sb.append(" : ");
        sb.append(software.getReleaseDate());
        sb.append("\n");
        sb.append("Version");
        sb.append(" : ");
        sb.append(software.getVersion().getVersion());
        sb.append("\n");
        sb.append("Home Page");
        sb.append(" : ");
        sb.append(software.getWebsite());

        return sb.toString();
    }
}
