package model;

import java.io.Serializable;

public class Supplier implements Serializable {
    private String name;
    private String address;
    private String country;
    private String vatId;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public Supplier(String name, String address, String country, String vatId) {
        this.name = name;
        this.address = address;
        this.country = country;
        this.vatId = vatId;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getVatId() {
        return vatId;
    }
    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    @Override
    public String toString() {
        return name;
    }
}
