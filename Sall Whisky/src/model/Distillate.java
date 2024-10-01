package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Distillate implements Serializable {
    private String newMakeNr;
    private double distillationTimeInHours;
    private double alcoholPercentage;
    private double amountInLiters;
    private String employee;
    private String description;
    List<Maltbatch> maltbatches = new ArrayList<>();

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public Distillate(String newMakeNr, double distillationTimeInHours, double alcoholPercentage,
                      double amountInLiters, String employee, List<Maltbatch> maltbatches, String description) {
        this.newMakeNr = newMakeNr;
        this.distillationTimeInHours = distillationTimeInHours;
        this.alcoholPercentage = alcoholPercentage;
        this.amountInLiters = amountInLiters;
        this.employee = employee;
        this.maltbatches = maltbatches;
        this.description = description;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public String getNewMakeNr() {
        return newMakeNr;
    }
    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }
    public String getDescription() {
        return description;
    }
    public double getAmountInLiters() {
        return amountInLiters;
    }
    public void setAmountInLiters(double amountInLiters) {
        this.amountInLiters = amountInLiters;
    }
    public List<Maltbatch> getMaltbatches() {
        return maltbatches;
    }
    public double getDistillationTimeInHours() {
        return distillationTimeInHours;
    }
    public String getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        String maltbatchesString = "";
        for (Maltbatch mb: maltbatches) {
            maltbatchesString += mb.getName() + ", ";
        }
        return newMakeNr + ", [ " + maltbatchesString + " ] - " + "(L: " + amountInLiters + ", Alc: " + alcoholPercentage + " Medarbejder: " + getEmployee() + " )";
    }
}
