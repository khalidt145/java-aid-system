
import java.io.Serializable;

public abstract class Beneficiary implements Serializable {

    private String id;
    private String name;
    private String city;

    public Beneficiary(String city, String id, String name) {
        this.city = city;
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    abstract String getType();

    @Override
    public boolean equals(Object o) {
        Beneficiary b = (Beneficiary) o;
        return this.id.equals(b.id);

    }

    @Override
    public String toString() {
        return "Id: " + getId() + " name: " + getName() + " city " + getCity();
    }
}
