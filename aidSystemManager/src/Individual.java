
import java.io.Serializable;

public class Individual extends Beneficiary implements Comparable<Individual>, Serializable {

    public Individual(String city, String id, String name, String status) {
        super(city, id, name);
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getType() {
        return "Individual";
    }

    @Override
    //compares alphabetically
    public int compareTo(Individual b) {
        return this.status.compareTo(b.status);
    }

    @Override
    public String toString() {
        return super.toString() + "Status: " + status;
    }

}
