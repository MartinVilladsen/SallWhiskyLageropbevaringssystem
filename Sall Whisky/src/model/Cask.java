package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cask implements Serializable {
    private int caskId;
    private String countryOfOrigin;
    private double sizeInLiters;
    private String previousContent;
    private double currentContentInLiters;
    private Position position;
    private CaskSupplier supplier;
    private final List<FillOnCask> currentFillOnCasks = new ArrayList<>();
    private final List<FillOnCask> previousFillOnCasks = new ArrayList<>();

    // ---------------------------------------------------------------------
    /** Constructor without Position */
    public Cask(String countryOfOrigin, double sizeInLiters, String previousContent) {
        this.countryOfOrigin = countryOfOrigin;
        this.sizeInLiters = sizeInLiters;
        this.previousContent = previousContent;
        this.currentContentInLiters = 0.0;
    }

    /** Constructor with previousContent */
    public Cask(int caskId, String countryOfOrigin, double sizeInLiters, String previousContent, Position position, CaskSupplier supplier) {
        this.caskId = caskId;
        this.countryOfOrigin = countryOfOrigin;
        this.sizeInLiters = sizeInLiters;
        this.previousContent = previousContent;
        this.position = position;
        this.supplier = supplier;
        this.currentContentInLiters = 0.0;
    }

    // ---------------------------------------------------------------------

    /**
     * Returns the tapFromDistillate that has been in the cask for the shortest amount of time
     * If the cask is empty, return null
     */
    public TapFromDistillate getYoungestTapFromDistillate() {
        if (currentFillOnCasks.isEmpty()) {
            return null;
        }

        TapFromDistillate youngestTapFromDistillate = currentFillOnCasks.get(0).getTapFromDistillate();
        for (FillOnCask fillOnCask : currentFillOnCasks) {
            if (fillOnCask.getTapFromDistillate().getFirstTimeOfFill().isAfter(youngestTapFromDistillate.getFirstTimeOfFill())) {
                youngestTapFromDistillate = fillOnCask.getTapFromDistillate();
            }
        }
        return youngestTapFromDistillate;
    }

    /** Calculates and returns the amount of fluids currently in the cask */
    public double getTotalLitersOfFills() {
        double totalLiters = 0;
        for (FillOnCask fillOnCask : currentFillOnCasks) {
            totalLiters += fillOnCask.getTapFromDistillate().getTotalLitersForFills();
        }
        return totalLiters;
    }

    /** Calculates and returns the weighted average alcoholpercentage for all the fillOnCasks in the cask */
    public double getTotalAlcoholPercentage() {
        double alcoholPercentage = 0;
        double totalFluids = 0;
        for (FillOnCask fillOnCask : currentFillOnCasks) {
            TapFromDistillate tapFromDistillate = fillOnCask.getTapFromDistillate();
            alcoholPercentage += (tapFromDistillate.getTotalLitersForFills() *
                    (tapFromDistillate.calculateAlcoholPercentage() / 100.0));
            totalFluids += tapFromDistillate.getTotalLitersForFills();
        }
        return alcoholPercentage / totalFluids * 100;
    }

    /** Calculates and returns the space left in the cask in liters */
    public double getLitersAvailable() {
        return sizeInLiters - getTotalLitersOfFills();
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public int getCaskId() {
        return caskId;
    }
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
    public List<FillOnCask> getCurrentFillOnCasks() {
        return currentFillOnCasks;
    }
    public List<FillOnCask> getPreviousPutOnCasks() {
        return previousFillOnCasks;
    }
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }
    public double getSizeInLiters() {
        return sizeInLiters;
    }
    public void setSizeInLiters(double sizeInLiters) {
        this.sizeInLiters = sizeInLiters;
    }
    public String getPreviousContent() {
        return previousContent;
    }
    public double getCurrentContentInLiters() {
        return currentContentInLiters;
    }
    public void setCurrentContentInLiters(double currentContentInLiters) {
        this.currentContentInLiters = currentContentInLiters;
    }
    public void setPreviousContent(String previousContent) {
        this.previousContent = previousContent;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public CaskSupplier getSupplier() {
        return supplier;
    }
    public void setSupplier(CaskSupplier supplier) {
        this.supplier = supplier;
    }
    public int getPositionId() {
        return position.getPositionId();
    }
    public int getShelfId() {
        return position.getShelf().getShelfId();
    }
    public int getRackId() {
        return position.getShelf().getRack().getRackId();
    }
    public int getWarehouseId() {
        return position.getShelf().getRack().getWarehouse().getWarehouseId();
    }
    public String getSupplierName() {
        return supplier.getName();
    }

    // ---------------------------------------------------------------------

    /** Add, Remove & toString */

    /** Adds a fillOnCask to the cask and updates the casks current content in liters */
    public void addCurrentFillOnCask(FillOnCask fillOnCask) {
        currentFillOnCasks.add(fillOnCask);
        currentContentInLiters += fillOnCask.getTapFromDistillate().getTotalLitersForFills();
    }
    public void addPreviousFillOnCask(FillOnCask fillOnCask) {
        previousFillOnCasks.add(fillOnCask);
    }
    public void removeCurrentFillOnCask(FillOnCask fillOnCask) {
        currentFillOnCasks.remove(fillOnCask);
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-17s | Total (L) %-5.2f | Tilgængelig (L) %-10.2f | %-18s ",
                caskId,
                countryOfOrigin,
                sizeInLiters,
                getLitersAvailable(),
                previousContent);
    }
}
