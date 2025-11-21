
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
