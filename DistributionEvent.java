package com.example.aidmanagerprojectgui2026;
//Serializable allows it to convert the data into byte code

import java.io.Serializable;
import java.util.GregorianCalendar;

public class DistributionEvent implements Serializable {

    //refrences to objects under AidItem and Beneficiary
    private AidItem item;
    private Beneficiary beneficiary;
    private GregorianCalendar date;

    public AidItem getItem() {
        return item;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public DistributionEvent(AidItem item, Beneficiary beneficiary, GregorianCalendar date) {
        this.item = item;
        this.beneficiary = beneficiary;
        this.date = date;
    }
    public String getBeneficiaryId() {
        return beneficiary.getId();
    }

    public String getBeneficiaryName() {
        return beneficiary.getName();
    }

    public String getItemCode() {
        return item.getCode();
    }

    public String getItemCategory() {
        return item.getCategory();
    }
    public String getDateString(){
        int d = date.get(GregorianCalendar.DAY_OF_MONTH);
        int m = date.get(GregorianCalendar.MONTH)+1;
        int y = date.get(GregorianCalendar.YEAR);
        return d+"/"+m+"/"+y;
    }



    @Override
    public String toString() {
        return "item: " + getItem() + " benificiary: " + getBeneficiary() + " date: " + getDate();
    }

}
