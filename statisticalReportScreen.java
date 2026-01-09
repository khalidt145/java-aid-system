package com.example.aidmanagerprojectgui2026;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class statisticalReportScreen extends VBox {

    private AidManager manager;
    private TextField citytxtField = new TextField();
    private Label mostCityLable = new Label("--");
    private Button cityGenerate = new Button("Generate");
    private Label totalDistrubiotionLable = new Label("0");
    private Label foodpackageLable = new Label("0");
    private Label medicalKitLable = new Label("0");
    private Label winterbagLable = new Label("0");
    private Label emergancykitLable = new Label("0");
    private DatePicker startDate = new DatePicker();
    private DatePicker endDate = new DatePicker();
    private Button generateBetweenDates = new Button("Generate");
    private Label betweenDatesLable = new Label("0");
    private ListView<String>betweenDatesView = new ListView<>();
    private Label familiesInCity = new Label("--");



    public statisticalReportScreen(AidManager manager){
        this.manager = manager;
        Label title = new Label("Statistical Reports");
        title.setFont(new Font("Arial Black", 33));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        citytxtField.setPromptText("enter city name");


        Label report1Lable = new Label("number of families sered in a city");
        report1Lable.setFont(new Font("Arial Black", 16));
        HBox row1 = new HBox(new Label("city: "),citytxtField,cityGenerate);
        row1.setAlignment(Pos.CENTER_LEFT);
        HBox row1Result = new HBox(new Label("result: "),familiesInCity);
        row1Result.setAlignment(Pos.CENTER_LEFT);
        VBox report1 = new VBox(report1Lable, row1, row1Result);

        Label report2Lable = new Label("Total aid items distributed");
        report2Lable.setFont(new Font("Arial Black", 16));

        HBox report2 = new HBox(new Label("Total: "), totalDistrubiotionLable);
        report2.setAlignment(Pos.CENTER_LEFT);

        Label report3Lable = new Label("Count aid Items by Category");
        report3Lable.setFont(new Font("Arial Black", 16));
        VBox categoriesBox = new VBox(new HBox(new Label("FoodPackages: "), foodpackageLable),
        new HBox(new Label("Medical Kits: "), medicalKitLable),
                new HBox(new Label("Winter Bags: "), winterbagLable),
                new HBox(new Label("Emergancy Kits: "), emergancykitLable));
        VBox report3 = new VBox(report3Lable, categoriesBox);

        Label report4Lable = new Label("Beneficiaries served between two dates: ");
        report4Lable.setFont(new Font("Arial Black", 16));

        HBox row4Input = new HBox(new Label("Start date: "), startDate, new Label("end date: "), endDate, generateBetweenDates);
        row4Input.setAlignment(Pos.CENTER_LEFT);
        HBox row4Count =new HBox(new Label("count: "),betweenDatesLable);
        row4Count.setAlignment(Pos.CENTER_LEFT);

            VBox report4 = new VBox(report4Lable, row4Input, row4Count, betweenDatesView);
        Label report5Lable= new Label("Most frequent City");
        report5Lable.setFont(new Font("Arial Black", 16));
        HBox report5 = new HBox(report5Lable,mostCityLable);
        report5.setAlignment(Pos.CENTER_LEFT);

            cityGenerate.setOnAction(e->runFamiliesInCity());
            generateBetweenDates.setOnAction(e->runBetweenDates());
        getChildren().addAll(titleBox,report1, report2,report3,report4, report5);
        refresh();





    }
    public void runFamiliesInCity(){
        String city = citytxtField.getText();
        if (city.isEmpty() || (!city.equalsIgnoreCase("gaza") && !city.equalsIgnoreCase("rafah")
           && !city.equalsIgnoreCase("tulkarm")&& !city.equalsIgnoreCase("jenin"))) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Error");
            error.setContentText("city input ie empty or not supported\nSupported cities: Rafah, Gaza, Tulkarm, Jenin");
            error.showAndWait();
            return;
        }
        int result = manager.numOfFamiliesServedInCity(city);
        familiesInCity.setText(String.valueOf(result));
    }
    public void runBetweenDates(){
        LocalDate s = startDate.getValue();
        LocalDate e = endDate.getValue();

        if (s == null||e == null){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("error");
            error.setContentText("start or end date is empty");
            error.showAndWait();
            return;
        }
        if (s.compareTo(e) > 0){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("error");
            error.setContentText("the start date is later than the end date");
            error.showAndWait();
            return;
        }
        GregorianCalendar start = new GregorianCalendar(s.getYear(), s.getMonthValue()-1,s.getDayOfMonth());
        GregorianCalendar end = new GregorianCalendar(e.getYear(),e.getMonthValue()-1, e.getDayOfMonth());
        int count = manager.beneficiariesServedBetweenDates(start,end);
        betweenDatesLable.setText(String.valueOf(count));
        ArrayList<String>ids = new ArrayList<>();
        ArrayList<String>lines = new ArrayList<>();

        for (int i = 0; i<manager.getDistributionEvents().size(); i++){
            DistributionEvent event = manager.getDistributionEvents().get(i);
            GregorianCalendar d = event.getDate();
            if (d.compareTo(start) >=0 && d.compareTo(end) <=0){
                String id = event.getBeneficiary().getId();
                if(!ids.contains(id)){
                    ids.add(id);
                    Beneficiary b = event.getBeneficiary();
                    lines.add(b.getId()+ "-"+b.getName()+"-"+b.getCity());
                }
            }
        }
        betweenDatesView.getItems().setAll(lines);

    }
    public void refresh(){
        totalDistrubiotionLable.setText(String.valueOf(manager.totalAidItemsDistributed()));
        foodpackageLable.setText(String.valueOf(manager.countAidItemsbyCategory("FoodPackage")));
        medicalKitLable.setText(String.valueOf(manager.countAidItemsbyCategory("MedicalKit")));
        winterbagLable.setText(String.valueOf(manager.countAidItemsbyCategory("WinterBag")));
        emergancykitLable.setText(String.valueOf(manager.countAidItemsbyCategory("EmergencyKit")));

        String city = manager.mostServedCity();
        if(city == null||city.trim().isEmpty()){
            mostCityLable.setText("--");
        }else {
            mostCityLable.setText(city);
        }
    }


}
