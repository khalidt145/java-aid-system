
import java.io.Serializable;

public class EmergencyKit extends AidItem implements Serializable {

    public EmergencyKit(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "EmergencyKit";
    }
}
