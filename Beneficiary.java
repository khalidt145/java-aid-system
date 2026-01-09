package com.example.aidmanagerprojectgui2026;

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
        if(this ==o){
            return true;
        }
        if (o==null){
            return false;

        }
        if(!(o instanceof Beneficiary)){
            return false;
        }
        Beneficiary b = (Beneficiary) o;
        if(this.id == null|| b.id == null){
            return false;
        }
        return this.id.equals(b.id);
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " name: " + getName() + " city " + getCity();
    }
}


 class Family extends Beneficiary implements Comparable<Family>, Serializable {

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



 class Individual extends Beneficiary implements Comparable<Individual>, Serializable {

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
