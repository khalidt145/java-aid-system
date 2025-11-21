
import java.io.Serializable;

public class FoodPackage extends AidItem implements Serializable {

    public FoodPackage(String code, String description) {
        super(code, description);
    }

    @Override
    public String getCategory() {
        return "FoodPackage";
    }

}
