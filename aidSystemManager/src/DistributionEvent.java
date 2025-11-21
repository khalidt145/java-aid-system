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

    @Override
    public String toString() {
        return "item: " + getItem() + " benificiary: " + getBeneficiary() + " date: " + getDate();
    }

}
