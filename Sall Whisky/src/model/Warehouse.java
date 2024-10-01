package model;

import controller.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Warehouse implements Observer, Serializable {
    private int warehouseId;
    private String address;
    private boolean isFilled = true;
    private List<Rack> racks = new ArrayList<>();

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public Warehouse(int warehouseId, String address) {
        this.address = address;
        this.warehouseId = warehouseId;
    }

    // ---------------------------------------------------------------------
    /** Methods */
    // ---------------------------------------------------------------------

    /** Checks if all the racks in the warehouse are full, if so, set isFilled to true. */
    public void update() {
        if (getAvailableRacks().isEmpty()) {
            isFilled = true;
        } else {
            isFilled = false;
        }
    }
    public boolean isFilled() {
        return isFilled;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    /** Return a list of all the racks in the warehouse that are not fully filled. */
    public List<Rack> getAvailableRacks() {
        List<Rack> availableRacks = new ArrayList<>();
        for (Rack rack : racks) {
            if (!rack.isFilled())
                availableRacks.add(rack);
        }
        return availableRacks;
    }
    public int getWarehouseId() {
        return warehouseId;
    }
    public String getAddress() {
        return address;
    }
    public List<Rack> getRacks() {
        return racks;
    }

    // ---------------------------------------------------------------------
    /** Adds & removes */
    // ---------------------------------------------------------------------

    public void addRack(Rack rack) {
        racks.add(rack);
    }
    public void removeRack(Rack rack) {
        racks.remove(rack);
    }

    @Override
    public String toString() {
        return "" + warehouseId;
    }
}
