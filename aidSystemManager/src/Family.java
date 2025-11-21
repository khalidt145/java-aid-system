
import java.io.Serializable;

public class Family extends Beneficiary implements Comparable<Family>, Serializable {

    private int memberCount;

    public Family(String city, String id, String name, int memberCount) {
        super(city, id, name);
        this.memberCount = memberCount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    public int compareTo(Family b) {
        if (this.memberCount > b.memberCount) {
            return 1;
        } else if (this.memberCount < b.memberCount) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String getType() {
        return "family";
    }

    @Override
    public String toString() {
        return super.toString() + " members: " + memberCount;
    }

}
