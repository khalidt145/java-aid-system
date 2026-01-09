package com.example.aidmanagerprojectgui2026;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

import java.util.GregorianCalendar;

public class distributionManagementScreen extends VBox {
    private AidManager manager;
    private ComboBox<Beneficiary> beneficiaryComboBox = new ComboBox<>();
    private ComboBox<AidItem> aidItemComboBox = new ComboBox<>();
    private DatePicker datePicker = new DatePicker();
    private Button addDistribution = new Button("Record Distribution");
    TableView<DistributionEvent> tableview = new TableView<>();

    public distributionManagementScreen(AidManager manager){
        this.manager = manager;

        Label title = new Label("distribution management screen");
        title.setFont(new Font("Arial black",33));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(7));

        beneficiaryComboBox.getItems().setAll(manager.getBeneficiaries());
        aidItemComboBox.getItems().setAll(manager.getAidItems());
        Label l1 = new Label("beneficiary: ");
        Label l2 = new Label("Aid item: ");
        Label l3 = new Label("date: ");
        gp.add(l1,0,0);
        gp.add(beneficiaryComboBox,1,0);
        gp.add(l2,0,1);
        gp.add(aidItemComboBox,1,1);
        gp.add(l3,0,2);
        gp.add(datePicker,1,2);
        HBox buttons = new HBox(addDistribution);
        buttons.setAlignment(Pos.CENTER_LEFT);
        prepTable();
        addDistribution.setOnAction(e->handleAdd());
        getChildren().addAll(titleBox,gp,buttons,tableview);
        refresh();
    }
    public void prepTable(){
        TableColumn<DistributionEvent, String> beneficiaryIDColom = new TableColumn<>("beneficiary ID");
        beneficiaryIDColom.setCellValueFactory(new PropertyValueFactory<>("beneficiaryId"));

        TableColumn<DistributionEvent,String>benificiaryNameColom = new TableColumn<>("beneficiary name: ");
        benificiaryNameColom.setCellValueFactory(new PropertyValueFactory<>("beneficiaryName"));

        TableColumn<DistributionEvent, String> itemCodeColom = new TableColumn<>("item Code: ");
        itemCodeColom.setCellValueFactory(new PropertyValueFactory<>("itemCode"));

        TableColumn<DistributionEvent,String>categoryColom = new TableColumn<>("Category Colom");
        categoryColom.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<DistributionEvent,String>dateColom = new TableColumn<>("Date: ");
        dateColom.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        tableview.getColumns().addAll(beneficiaryIDColom,benificiaryNameColom,itemCodeColom,categoryColom,dateColom);
    }

    public void handleAdd(){
        Beneficiary beneficiary =  beneficiaryComboBox.getValue();
        AidItem item = aidItemComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (beneficiary == null||item == null|| date == null){
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Error");
        error.setContentText("one or more of the inputs is empty");
        error.showAndWait();
        return;
        }
        GregorianCalendar d = new GregorianCalendar(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
        DistributionEvent event = new DistributionEvent(item,beneficiary,d);
        manager.getDistributionEvents().add(event);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("success");
        info.setContentText("distribution even has been added successfully");
        info.showAndWait();

        clearFields();
        refreshTable();

    }
    public void clearFields(){
        beneficiaryComboBox.getSelectionModel().clearSelection();
        aidItemComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }
    public void refreshTable(){
        tableview.getItems().setAll(manager.getDistributionEvents());
    }
    public void refresh(){
        beneficiaryComboBox.getItems().setAll(manager.getBeneficiaries());
        aidItemComboBox.getItems().setAll(manager.getAidItems());
        refreshTable();
    }


}
