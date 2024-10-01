package model;

import java.io.Serializable;

public class StorageCounter implements Serializable {
        private int warehouseCount = 0;
        private int rackCount = 0;
        private int shelfCount = 0;
        private int positionCount = 0;
        private int caskCount = 0;
        private int whiskyBottleCount = 0;

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public int getWarehouseCount() {
        return warehouseCount;
    }
    public int getRackCount() {
        return rackCount;
    }
    public int getShelfCount() {
        return shelfCount;
    }
    public int getPositionCount() {
        return positionCount;
    }
    public int getCaskCount() {
        return caskCount;
    }
    public int getWhiskyBottleCount() {
        return whiskyBottleCount;
    }

    // ---------------------------------------------------------------------
    /** Increment methods */
    // ---------------------------------------------------------------------

    public void incrementWarehouseCount() {
        warehouseCount++;
    }
    public void incrementRackCount() {
        rackCount++;
    }
    public void incrementShelfCount() {
        shelfCount++;
    }
    public void incrementPositionCount() {
        positionCount++;
    }
    public void incrementCaskCount() {
        caskCount++;
    }
    public void incrementWhiskyBottleCount() {
        whiskyBottleCount++;
    }
}
