package com.example.aidmanagerprojectgui2026;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Dashboard extends VBox {
    private AidManager manager;
    private Label totalBeneficiaries = new Label("0");
    private Label totalAidItems = new Label("0");
    private Label totalDistribution = new Label("0");
    private Label mostFrequentCity= new Label("--");

    public Dashboard(AidManager manager){
        this.manager = manager;
       totalBeneficiaries.setFont(new Font("bold", 18));
        totalAidItems.setFont(new Font("bold", 18));
        totalDistribution.setFont(new Font("bold", 18));
        mostFrequentCity.setFont(new Font("bold", 18));
        setSpacing(30);
        setAlignment(Pos.BASELINE_LEFT);
        Label title = new Label("HomeScreen");
        title.setFont(new Font("Arial Black",33));
        title.setAlignment(Pos.TOP_CENTER);
        HBox titlebox = new HBox(title);
        titlebox.setAlignment(Pos.TOP_CENTER);
        titlebox.setPadding(new Insets(0,0,70,0));
        Label l1 = new Label("Total number of Beneficiaries:");
        l1.setFont(new Font("Arial Bold", 18));
        HBox row1 = new HBox(7);
        row1.getChildren().addAll(l1, totalBeneficiaries);

        Label l2 = new Label("Total number of aid items:");
        l2.setFont(new Font("Arial Bold", 18));
        HBox row2 = new HBox(7);
        row2.getChildren().addAll(l2, totalAidItems);

        Label l3 = new Label("Total Distribution:");
        l3.setFont(new Font("Arial Bold", 18));
        HBox row3 = new HBox(7);
        row3.getChildren().addAll(l3, totalDistribution);

        Label l4 = new Label("Most frequently served city:");
        l4.setFont(new Font("Arial Bold", 18));
        HBox row4 = new HBox(7);
        row4.getChildren().addAll(l4, mostFrequentCity);

        getChildren().addAll(titlebox,row1,row2,row3,row4);

        //refresh the data shown in the lables when the screen changes
    refresh();
    }
public void refresh(){
        totalBeneficiaries.setText(String.valueOf(manager.getBeneficiaries().size()));
        totalAidItems.setText(String.valueOf(manager.getAidItems().size()));
        totalDistribution.setText(String.valueOf(manager.getDistributionEvents().size()));
    String city = manager.mostServedCity();
        if (city == null || city.trim().isEmpty()){
            mostFrequentCity.setText("--");
        }else{
            mostFrequentCity.setText(city);
        }
}



}


