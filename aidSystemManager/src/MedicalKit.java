
import java.io.Serializable;

public class MedicalKit extends AidItem implements Serializable {

    public MedicalKit(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "MedicalKit";
    }
}
