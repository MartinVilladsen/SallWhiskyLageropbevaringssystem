package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WhiskyFill implements Serializable {
    private double amountofDistilateFillInLiters;
    private List<TapFromDistillate> tapFromDistillates = new ArrayList<>();
    private LocalDate timeOfFill;
    private double alcoholPercentage;
    private Cask cask;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public WhiskyFill(double amountofDistilateFillInLiters, List<TapFromDistillate> tapFromDistillates, LocalDate timeOfFill, double alcoholPercentage, Cask cask) {
        this.amountofDistilateFillInLiters = amountofDistilateFillInLiters;
        this.tapFromDistillates = tapFromDistillates;
        this.timeOfFill = timeOfFill;
        this.alcoholPercentage = alcoholPercentage;
        this.cask = cask;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public double getAmountofDistilateFillInLiters() {
        return amountofDistilateFillInLiters;
    }
    public List<TapFromDistillate> getTapFromDestillates() {
        return tapFromDistillates;
    }
    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }
    public LocalDate getTimeOfFill() {
        return timeOfFill;
    }
    public Cask getCask() {
        return cask;
    }
    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    @Override
    public String toString() {
        String distillates = "";
        for (TapFromDistillate tapFromDistillate : tapFromDistillates) {
            for (DistillateFill distillateFill: tapFromDistillate.getDistillateFills()) {
                distillates += "NewMake: " + distillateFill.getDistillate().getNewMakeNr() + ", ";
            }
        }
        return "Liter: " + amountofDistilateFillInLiters + " | Alc% :" + alcoholPercentage + "\n" + "Destillater: " + distillates;
    }
}
