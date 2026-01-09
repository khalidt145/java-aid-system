package com.example.aidmanagerprojectgui2026;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private AidManager manager;

    private Dashboard dashboard;
    private BeneficiaryManagementScreen beneficiaryScreen;
    private aidItemManagementScreen aidItemScreen;
    private distributionManagementScreen distributionScreen;
    private FIleOperationsScreen fileScreen;
    private statisticalReportScreen statsScreen;

    private BorderPane bp;

    @Override
    public void start(Stage stage) {

        manager = new AidManager();

        dashboard = new Dashboard(manager);
        beneficiaryScreen = new BeneficiaryManagementScreen(manager);
        aidItemScreen = new aidItemManagementScreen(manager);
        distributionScreen = new distributionManagementScreen(manager);
        fileScreen = new FIleOperationsScreen(manager);
        statsScreen = new statisticalReportScreen(manager);

        bp = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("screens");

        MenuItem homeItem = new MenuItem("Dashboard");
        MenuItem beneficiaryItem = new MenuItem("Beneficiaries");
        MenuItem aidItem = new MenuItem("aid Items");
        MenuItem distributionItem = new MenuItem("Distributions");
        MenuItem statsItem = new MenuItem("Statistics");
        MenuItem fileItem = new MenuItem("File operations");

        //call screens and refresh them
        homeItem.setOnAction(e -> {
            dashboard.refresh();
            bp.setCenter(dashboard);
        });

        beneficiaryItem.setOnAction(e -> {
            bp.setCenter(beneficiaryScreen);
        });

        aidItem.setOnAction(e -> {
            bp.setCenter(aidItemScreen);
        });

        distributionItem.setOnAction(e -> {
            distributionScreen.refresh();
            bp.setCenter(distributionScreen);
        });

        statsItem.setOnAction(e -> {
            statsScreen.refresh();
            bp.setCenter(statsScreen);
        });

        fileItem.setOnAction(e -> {
            bp.setCenter(fileScreen);
        });

        menu.getItems().addAll(homeItem, beneficiaryItem, aidItem, distributionItem, statsItem, fileItem);
        menuBar.getMenus().add(menu);
        bp.setTop(menuBar);

        dashboard.refresh();
        //make the dashboard the home screen
        bp.setCenter(dashboard);

        Scene scene = new Scene(bp, 1080, 750);
        stage.setTitle("Aid Manager GUI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
