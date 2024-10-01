package model;

import java.io.Serializable;

public class GrainSupplier extends Supplier implements Serializable {

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public GrainSupplier(String name, String address, String country, String vatId) {
        super(name, address, country, vatId);
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    @Override
    public String getName() {
        return super.getName();
    }
    @Override
    public void setName(String name) {
        super.setName(name);
    }
    @Override
    public String getAddress() {
        return super.getAddress();
    }
    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }
    @Override
    public String getCountry() {
        return super.getCountry();
    }
    @Override
    public void setCountry(String country) {
        super.setCountry(country);
    }
    @Override
    public String getVatId() {
        return super.getVatId();
    }
    @Override
    public void setVatId(String vatId) {
        super.setVatId(vatId);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
