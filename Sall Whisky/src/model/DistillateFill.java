package model;

import java.io.Serializable;

public class DistillateFill implements Serializable {
    private double amountOfDistillateInLiters;
    private Distillate distillate;
    private TapFromDistillate tapFromDistillate;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public DistillateFill(double amountOfDistillateInLiters, Distillate distillate) {
        this.amountOfDistillateInLiters = amountOfDistillateInLiters;
        this.distillate = distillate;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public TapFromDistillate getFillOnCask() {
        return tapFromDistillate;
    }
    public void setFillOnCask(TapFromDistillate tapFromDistillate) {
        this.tapFromDistillate = tapFromDistillate;
    }
    public double getAmountOfDistillateInLiters() {
        return amountOfDistillateInLiters;
    }
    public Distillate getDistillate() {
        return distillate;
    }
    public void setDistillate(Distillate distillate) {
        this.distillate = distillate;
    }
}
