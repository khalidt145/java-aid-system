package com.example.aidmanagerprojectgui2026;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class BeneficiaryManagementScreen extends VBox {
    private AidManager manager;
    private TextField id = new TextField();
    private TextField name = new TextField();
    private TextField city = new TextField();
    private TextField memberNum = new TextField();
    private Label memberLable = new Label("members number: ");
    private TextField status = new TextField();
    private Label statusLable = new Label("indivdual status: ");
    private Button addBeneficiary = new Button("Add beneficiary");
    private Button clear = new Button("Clear");
    private TableView<Beneficiary> tableView = new TableView<>();
    private ComboBox<String> typeBox = new ComboBox<>();
    Label title = new Label("Beneficiary management");

    public BeneficiaryManagementScreen(AidManager manager){
        this.manager = manager;

    setAlignment(Pos.TOP_CENTER);
        title.setFont(new Font("Arial Black", 33));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(0,0,70,0));

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(7));

        typeBox.getItems().addAll("family", "individual");

        //grid pane
        Label l1 = new Label("type: ");
        Label l2 = new Label("id: ");
        Label l3 = new Label("name: ");
        Label l4 = new Label("city: ");
        gp.add(l1, 0,0);
        gp.add(typeBox, 1, 0);

        gp.add(l2, 0,1);
        gp.add(id, 1,1);

        gp.add(l3,0,2);
        gp.add(name,1,2);

        gp.add(l4,0,3);
        gp.add(city,1,3);

        gp.add(memberLable , 0, 4);
        gp.add(memberNum, 1, 4);

        gp.add(statusLable, 0 , 5);
        gp.add(status, 1, 5);

        HBox buttons = new HBox(addBeneficiary, clear);
        buttons.setAlignment(Pos.CENTER_LEFT);
        //buttons.setPadding(new Insets(10,0,10,0));
        prepTableView();
        typeBox.setOnAction(e -> updateFields());
        updateFields();

        addBeneficiary.setOnAction(e -> BeneficiaryHandler());
        clear.setOnAction(e -> clearFields());
        getChildren().addAll(titleBox, gp, buttons, tableView);
        refresh();

    }
private void prepTableView(){
    TableColumn<Beneficiary, String> idColom = new TableColumn<>("id: ");
    idColom.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<Beneficiary, String> nameColom = new TableColumn<>("Name: ");
    nameColom.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Beneficiary, String> cityColom = new TableColumn<>("City: ");
    cityColom.setCellValueFactory(new PropertyValueFactory<>("city"));

    tableView.getColumns().addAll(idColom,nameColom,cityColom);


}
private void BeneficiaryHandler() {
    String typetxt = typeBox.getValue();
    String idtxt = id.getText();
    String nametxt = name.getText();
    String citytxt = city.getText();

    //validations for the input
    if (typetxt == null ||typetxt.isEmpty() || idtxt.isEmpty() || nametxt.isEmpty() || citytxt.isEmpty()) {
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Error");
        error.setContentText("please fill all the text fields");
        error.showAndWait();
        return;
    }
    if (manager.searchBeneficiarie(idtxt) != null) {
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Error");
        error.setContentText("this id already exists, use another id");
        error.showAndWait();
        return;
    }
    if (!citytxt.equalsIgnoreCase("rafah") && !citytxt.equalsIgnoreCase("gaza")
            && !citytxt.equalsIgnoreCase("tulkarm") && !citytxt.equalsIgnoreCase("jenin")) {
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Error");
        error.setContentText("this city isnt available yet, available cities are gaza, rafah, tulkarm and jenin");
        error.showAndWait();
    return;
    }
    Beneficiary b;
    if (typetxt.equalsIgnoreCase("family")) {
        String membertxt = memberNum.getText().trim();
        if (membertxt.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Error");
            error.setContentText("member number is empty");
            error.showAndWait();
            return;
        }
        int members;
        try {
            members = Integer.parseInt(membertxt);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("error");
            error.setContentText("member number must be a number");
            error.showAndWait();
            return;
        }
        b = new Family(citytxt, idtxt, nametxt, members);
        manager.getBeneficiaries().add(b);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("info");
        info.setContentText("beneficiary added successfully");
        info.showAndWait();
        clearFields();
        refresh();

    }else {
        String statustxt = status.getText().trim();
        if (statustxt.isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("error");
            error.setContentText("status is empty");
            error.showAndWait();
            return;
        }
        b = new Individual(citytxt, idtxt, nametxt, statustxt);
        manager.getBeneficiaries().add(b);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("info");
        info.setContentText("beneficiary added successfully");
        info.showAndWait();
        clearFields();
        refresh();
    }



}
private void updateFields(){
        String type = typeBox.getValue();
        if (type == null){
            return;
        }
        boolean isFamily = type.equalsIgnoreCase("family");
    memberLable.setVisible(isFamily);
    memberLable.setManaged(isFamily);
    memberNum.setVisible(isFamily);
    memberNum.setManaged(isFamily);
    statusLable.setVisible(!isFamily);
    statusLable.setManaged(!isFamily);
    status.setVisible(!isFamily);
    status.setManaged(!isFamily);

}
private void clearFields(){
        id.clear();
        city.clear();
        name.clear();
        memberNum.clear();
        status.clear();
}
public void refresh(){
        if(manager != null){
            tableView.getItems().setAll(manager.getBeneficiaries());
        }
}

}
