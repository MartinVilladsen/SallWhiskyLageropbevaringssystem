package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Whisky implements Serializable {
    private String name;
    private double waterInLiters;
    private List<WhiskyFill> whiskyFills = new ArrayList<>();
    private String description;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    /** Without whiskyFills */
    public Whisky(String name) {
        this.name = name;
    }

    /** With whiskyFills*/
    public Whisky(String name, double waterInLiters, List<WhiskyFill> whiskyFills, String description) {
        this.name = name;
        this.waterInLiters = waterInLiters;
        this.whiskyFills = whiskyFills;
        this.description = description;
    }

    // ---------------------------------------------------------------------
    /** Methods */
    // ---------------------------------------------------------------------

    /** Returns a HashMap that shows the of each whiskyfill in the whisky */
    public Map<WhiskyFill, Double> caskShare() {
        Map<WhiskyFill, Double> map = new HashMap();

        for (WhiskyFill whiskyFill : whiskyFills) {
            double share = (whiskyFill.getAmountofDistilateFillInLiters() / totalFluidsInWhisky()) * 100;
            DecimalFormat df = new DecimalFormat("##,##");
            share = Double.parseDouble(df.format(share));
            map.put(whiskyFill, share);
        }
        return map;
    }

    /**
     * Calculates and returns the alcoholpercentage for the whisky
     * Pre: totalFluids > waterInLiters, totalFluids > 0
     * Pre: waterInLiters >= 0
     */
    public double calculateAlcoholPercentage() {
        double alcoholPercentage = 0;
        for (WhiskyFill whiskyFill : whiskyFills) {
            alcoholPercentage += (whiskyFill.getAmountofDistilateFillInLiters() *
                    (whiskyFill.getAlcoholPercentage() / 100.0));
        }
        return alcoholPercentage / totalFluidsInWhisky() * 100;
    }

    /** Calculates the total amount of Whisky with water included. */
    public double totalFluidsInWhisky() {
        double totalFluids = waterInLiters;
        for (WhiskyFill whiskyfill : whiskyFills) {
            totalFluids += whiskyfill.getAmountofDistilateFillInLiters();
        }
        return totalFluids;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public String getName() {
        return name;
    }
    public List<WhiskyFill> getWhiskyFills() {
        return whiskyFills;
    }
    public double getWaterInLiters() {
        return waterInLiters;
    }
    public String getDescription() {
        return description;
    }
    public void setWaterInLiters(double waterInLiters) {
        this.waterInLiters = waterInLiters;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public void addWhiskyFill(WhiskyFill whiskyFill) {
        whiskyFills.add(whiskyFill);
    }

    @Override
    public String toString() {
        String newMakes = "";
        String timeOfFillString = "";
        if (!whiskyFills.isEmpty()) {
            timeOfFillString += whiskyFills.get(0).getTimeOfFill();
        }

        for (WhiskyFill whiskyFill: whiskyFills) {
            for (TapFromDistillate tapFromDistillate : whiskyFill.getTapFromDestillates()) {
                for (DistillateFill distillateFill: tapFromDistillate.getDistillateFills()) {
                    newMakes += distillateFill.getDistillate().getNewMakeNr() + ", ";
                }
            }
        }
        return name + " (" + timeOfFillString + "), Newmake: " + newMakes;
    }
}
