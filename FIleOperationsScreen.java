package com.example.aidmanagerprojectgui2026;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;

public class FIleOperationsScreen extends VBox {
    private AidManager manager;
    public FIleOperationsScreen(AidManager manager){
        this.manager = manager;
        setSpacing(30);
        Label title = new Label("File operations screen");
        title.setAlignment(Pos.TOP_CENTER);
        title.setFont(new Font("Arial Black",33));
        HBox titlebox = new HBox(title);
        titlebox.setAlignment(Pos.TOP_CENTER);
        titlebox.setPadding(new Insets(0,0,40,0));
        Button saveToTextFile = new Button("Save to text file");
        Button loadFromTextFile = new Button("Load from text file");
        Button SavetoBinary = new Button("Save to binary File");
        Button loadFromBinary = new Button("Load from binary File");
    saveToTextFile.setOnAction(e -> SaveToTxt());
    loadFromTextFile.setOnAction(e->LoadFromTxt());
    SavetoBinary.setOnAction(e->saveToBinary());
    loadFromBinary.setOnAction(e->loadFromBinary());

    getChildren().addAll(titlebox,saveToTextFile, SavetoBinary, loadFromBinary,loadFromTextFile);
    }



    //methods
    private void SaveToTxt() {
        String FileName = "aidData.txt";
        try {
            manager.saveToTextFile(FileName);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("info");
            success.setContentText("data has been successfully saved to file: "+FileName);
            success.showAndWait();
        } catch (IOException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("error");
            warning.setContentText("an error has occurred");
            warning.showAndWait();
        }
    }
    private void LoadFromTxt()  {
        String fileName ="aidData.txt";
        try {
            manager.loadFromTextFile(fileName);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("info");
            success.setContentText("data has been successfully loaded from file: "+fileName);
            success.showAndWait();
        }catch (IOException e){
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("error");
            warning.setContentText("an error has occurred");
            warning.showAndWait();
        }
    }
    private void saveToBinary(){
        String fileName = "aidData.dat";
        try {
            manager.saveToBinaryFile(fileName);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("info");
            success.setContentText("data has been successfully saved to file: "+fileName);
            success.showAndWait();
        }catch (IOException e){
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("error");
            warning.setContentText("an error has occurred");
            warning.showAndWait();
        }
    }
    private void loadFromBinary(){
        String fileName = "aidData.dat";
        try {
            manager.loadFromBinaryFile(fileName);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("info");
            success.setContentText("data has been successfully saved from file: "+fileName);
            success.showAndWait();
        } catch (IOException e) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("error");
            warning.setContentText("an error has occurred");
            warning.showAndWait();
        }
    }

}

