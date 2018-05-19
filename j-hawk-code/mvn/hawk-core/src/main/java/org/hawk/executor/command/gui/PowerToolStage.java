/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.executor.command.gui;

import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.common.di.AppContainer;
import org.commons.event.exception.HawkEventException;
import org.hawk.executor.command.plugin.available.AvailablePluginHtmlJavaBean;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.IHawkPluginService;
import org.hawk.plugin.exception.HawkPluginException;
import org.hawk.software.SoftwareServiceImpl;

/**
 *
 * @author reemeeka
 */
public class PowerToolStage extends Stage {

    private final ObservableList<PowerToolVO> availablePowerTools
            = FXCollections.observableArrayList();
    private final ObservableList<PowerToolVO> downloadedPowerTools
            = FXCollections.observableArrayList();
    private final ObservableList<PowerToolVO> installedPowerTools
            = FXCollections.observableArrayList();

    public PowerToolStage() {
        this.setTitle("PowerTool");
        this.initModality(Modality.APPLICATION_MODAL);
        Group root = new Group();
        Scene scene = new Scene(root, 700, 500, Color.WHITE);
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < 3; i++) {
            Tab tab = new Tab();
            tab.setClosable(false);
            HBox hbox = new HBox();
            if (i == 0) {
                tab.setText("Available");
                TableView availTableView = this.getTabView();
                availTableView.setItems(availablePowerTools);
                populateAvailablePowerTools();
                hbox.getChildren().add(availTableView);

                VBox vbox = new VBox();
                hbox.getChildren().add(vbox);
                Label description = new Label();
                description.setText("Available");
                vbox.getChildren().add(description);
                description.setPrefHeight(400);
                Button downloadButton = new Button("Download");
                vbox.getChildren().add(downloadButton);
                downloadButton.setAlignment(Pos.BASELINE_RIGHT);
                downloadButton.setOnAction(event->{
                    IHawkPluginService hawkPluginService = AppContainer.getInstance().getBean(IHawkPluginService.class);
                    try {
                        PowerToolVO selectedPowerToolVO = (PowerToolVO) availTableView.getSelectionModel().getSelectedItem();
                        AvailablePluginHtmlJavaBean availablePluginHtmlJavaBean = selectedPowerToolVO.getAvailablePluginHtmlJavaBean();
                        hawkPluginService.downloadPlugin(availablePluginHtmlJavaBean.getPath());
                    } catch (HawkEventException ex) {
                        Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (HawkPluginException ex) {
                        Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } 
            hbox.setAlignment(Pos.CENTER_LEFT);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        this.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logo.png")));
        this.setScene(scene);

    }

    private TableView getTabView() {
        TableView availTableView = new TableView();
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<PowerToolVO, String>("name"));
        TableColumn versionColumn = new TableColumn("Version");
        versionColumn.setCellValueFactory(new PropertyValueFactory<PowerToolVO, String>("version"));
        TableColumn creatorColumn = new TableColumn("Creator");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<PowerToolVO, String>("creator"));
        availTableView.getColumns().addAll(nameColumn, versionColumn, creatorColumn);
        return availTableView;
    }

    private void populateInstalledPowerTools() {
        IHawkPluginService hawkPluginService = AppContainer.getInstance().getBean(IHawkPluginService.class);
        try {
            Set<HawkPlugin> hawkPlugins = hawkPluginService.findInstalledPlugins();
            if (hawkPlugins != null) {
                hawkPlugins.forEach(hawkPlugin -> {
                    PowerToolVO powerToolVO = new PowerToolVO(hawkPlugin);
                    installedPowerTools.add(powerToolVO);
                });
            }
        } catch (HawkPluginException ex) {
            Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HawkEventException ex) {
            Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateDownloadedPowerTools() {
        IHawkPluginService hawkPluginService = AppContainer.getInstance().getBean(IHawkPluginService.class);
        try {
            Set<HawkPlugin> hawkPlugins = hawkPluginService.findDownloadedPlugins();
            if (hawkPlugins != null) {
                hawkPlugins.forEach(hawkPlugin -> {
                    PowerToolVO powerToolVO = new PowerToolVO(hawkPlugin);
                    downloadedPowerTools.add(powerToolVO);
                });
            }
        } catch (HawkPluginException ex) {
            Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HawkEventException ex) {
            Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateAvailablePowerTools() {
        IHawkPluginService hawkPluginService = AppContainer.getInstance().getBean(IHawkPluginService.class);
        try {
            Set<AvailablePluginHtmlJavaBean> availablePluginHtmlJavaBeans = hawkPluginService.showAvailablePlugins();
            if (availablePluginHtmlJavaBeans != null) {
                availablePluginHtmlJavaBeans.forEach(hawkPlugin -> {
                    PowerToolVO powerToolVO = new PowerToolVO(hawkPlugin);
                    availablePowerTools.add(powerToolVO);
                });
            }
        } catch (HawkPluginException ex) {
            Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HawkEventException ex) {
            Logger.getLogger(PowerToolStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
