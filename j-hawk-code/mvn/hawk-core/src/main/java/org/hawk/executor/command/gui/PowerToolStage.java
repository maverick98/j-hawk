/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.executor.command.gui;

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

/**
 *
 * @author reemeeka
 */
public class PowerToolStage extends Stage {

    private final ObservableList<PowerToolVO> availablePlugins
            = FXCollections.observableArrayList(
                    new PowerToolVO("HawkEye", "18.05", "Manoranjan Sahu"),
                    new PowerToolVO("Reetika", "18.05", "Reetika Sahoo"),
                    new PowerToolVO("Meehika", "18.05", "Meehika Sahoo")
            );
    private final ObservableList<PowerToolVO> downloadedPlugins
            = FXCollections.observableArrayList(
                    new PowerToolVO("HawkEye", "18.05", "Manoranjan Sahu"),
                    new PowerToolVO("Reetika", "18.05", "Reetika Sahoo"),
                    new PowerToolVO("Meehika", "18.05", "Meehika Sahoo")
            );

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
                availTableView.setItems(availablePlugins);
                hbox.getChildren().add(availTableView);

                VBox vbox = new VBox();
                hbox.getChildren().add(vbox);
                Label description = new Label();
                description.setText("Available");
                vbox.getChildren().add(description);
                description.setPrefHeight(400);
                Button installButton = new Button("Download");
                vbox.getChildren().add(installButton);
                installButton.setAlignment(Pos.BASELINE_RIGHT);
            } else if (i == 1) {
                tab.setText("Downloaded");
                TableView downloadedTabView = this.getTabView();
                downloadedTabView.setItems(downloadedPlugins);
                hbox.getChildren().add(downloadedTabView);
                VBox vbox = new VBox();
                hbox.getChildren().add(vbox);
                Label description = new Label();
                description.setText("Downloaded");
                vbox.getChildren().add(description);
                description.setPrefHeight(400);
                Button installButton = new Button("Install");
                vbox.getChildren().add(installButton);
                installButton.setAlignment(Pos.BASELINE_RIGHT);

            } else if (i == 2) {
                tab.setText("Installed");
                hbox.getChildren().add(new Label("Installed"));
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

}
