package com.example.aidmanagerprojectgui2026;

import java.io.Serializable;

public abstract class AidItem implements Serializable {

    private String code;
    private String description;

    public AidItem(String code, String description) {
        this.code = code;
        this.description = description;
    }

    //no need for setter because code and description shouldn't change
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getCategory();

    //to read from the array list
    @Override
    public String toString() {
        return "Code: " + getCode() + " Description: " + getDescription();
    }

}


 class FoodPackage extends AidItem implements Serializable {

    public FoodPackage(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "FoodPackage";
    }

}


 class MedicalKit extends AidItem implements Serializable {

    public MedicalKit(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "MedicalKit";
    }
}


 class WinterBag extends AidItem implements Serializable {

    public WinterBag(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "WinterBag";
    }
}


 class EmergencyKit extends AidItem implements Serializable {

    public EmergencyKit(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "EmergencyKit";
    }
}



