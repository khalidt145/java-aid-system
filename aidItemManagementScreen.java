package com.example.aidmanagerprojectgui2026;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.lang.classfile.instruction.SwitchCase;


public class aidItemManagementScreen extends VBox {
    private final AidManager manager;
    private TextField codetxtField = new TextField();
    private TextField descriptiontxtField = new TextField();
    private ComboBox <String> categoryBox = new ComboBox<>();
    private TableView<AidItem>aidTable = new TableView<>();
    private Button addAidButton = new Button("Add Aid item");
    public aidItemManagementScreen(AidManager manager){
        this.manager = manager;
        Label title = new Label("Aid item management");
        title.setFont(new Font("Arial black",33));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(0,0,40,0));

        GridPane gp = new GridPane();

        categoryBox.getItems().addAll ("FoodPackage","MedicalKit","WinterBag", "EmergencyKit");
         Label l1 = new Label("code: ");
         Label l2 = new Label("Description: ");
         Label l3 = new Label("Category: ");

         gp.add(l1,0,0);
         gp.add(codetxtField,1,0);

         gp.add(l2,0,1);
        gp.add(descriptiontxtField,1,1);

        gp.add(l3,0,2);
        gp.add(categoryBox,1,2);


        HBox buttons = new HBox(addAidButton);
        buttons.setAlignment(Pos.BASELINE_LEFT);

        prepTable();

        addAidButton.setOnAction(e->handleAdd());
        getChildren().addAll(titleBox,gp,buttons,aidTable);
        refresh();


    }
    private void prepTable(){
        TableColumn<AidItem, String> codeColom = new TableColumn<>("code: ");
        codeColom.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<AidItem, String> descColom = new TableColumn<>("Description: ");
        descColom.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<AidItem, String> categoryColom = new TableColumn<>("Category: ");
        categoryColom.setCellValueFactory(new PropertyValueFactory<>("category"));

        aidTable.getColumns().addAll(codeColom, descColom, categoryColom);
    }
    private void handleAdd(){
        String code = codetxtField.getText();
        String description = descriptiontxtField.getText();
        String category = categoryBox.getValue();

        //validation
        if (code.isEmpty()||description.isEmpty()||category == null||category.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Error");
            error.setContentText("Error: one or more fields is empty try again");
            error.showAndWait();
            return;
        }
        boolean dupe = false;
        for (int i = 0; i < manager.getAidItems().size(); i++){
            AidItem item = manager.getAidItems().get(i);
            if (item.getCode().equals(code)){
                dupe = true;
                break;
            }

        }
        if (dupe == true){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Error");
            error.setContentText("Error: this code already exists");
            error.showAndWait();
            return;
        }
        AidItem b = null;
        switch(category.toLowerCase()) {
            case "foodpackage":
                b = new FoodPackage(code, description);
                break;
            case "winterbag":
                b = new WinterBag(code, description);
                break;
            case "medicalkit":
                b = new MedicalKit(code, description);
                break;
            case "emergencykit":
                b = new EmergencyKit(code, description);
                break;
        }
        manager.getAidItems().add(b);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Success");
        info.setContentText("item has been added successfully");
        info.showAndWait();

        clear();
        refresh();
    }
    private void clear(){
        codetxtField.clear();
        descriptiontxtField.clear();
        categoryBox.getSelectionModel().clearSelection();
    }
    private void refresh(){
        aidTable.getItems().setAll(manager.getAidItems());
    }



}
