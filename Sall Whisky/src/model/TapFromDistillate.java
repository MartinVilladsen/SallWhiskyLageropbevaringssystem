package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TapFromDistillate implements Serializable {
    private LocalDate firstTimeOfFill;
    private ArrayList<FillOnCask> fillOnCasks = new ArrayList<>();
    private List<DistillateFill> distillateFills = new ArrayList<>();

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public TapFromDistillate(LocalDate firstTimeOfFill, Cask cask) {
        this.firstTimeOfFill = firstTimeOfFill;
        fillOnCasks.add(new FillOnCask(firstTimeOfFill, this, cask));
    }

    // ---------------------------------------------------------------------
    /** Methods */
    // ---------------------------------------------------------------------

    /**
     * Calculates and returns a hashMap that shows how big a share each distillate has of the
     * total amount in the fillOnCask
     */
    public Map<DistillateFill, Double> distillateShare() {
        Map<DistillateFill, Double> map = new HashMap<>();
        double totalLiters = getTotalLitersForFills();

        for (DistillateFill fill : distillateFills) {
            double share = (fill.getAmountOfDistillateInLiters() / totalLiters) * 100;
            DecimalFormat df = new DecimalFormat("##.##");
            share = Double.parseDouble(df.format(share));
            map.put(fill, share);
        }
        return map;
    }
    /** Calculates and returns the alcohol percentage in distillateFills */
    public double calculateAlcoholPercentage() {
        double alcoholPercentage = 0;
        double totalFluids = 0;
        for (DistillateFill distillateFill : distillateFills) {
            alcoholPercentage += (distillateFill.getAmountOfDistillateInLiters() *
                    (distillateFill.getDistillate().getAlcoholPercentage() / 100.0));
            totalFluids += distillateFill.getAmountOfDistillateInLiters();
        }
        return alcoholPercentage / totalFluids * 100;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    /** Returns the total sum of liters from distillatefills connected to this object */
    public double getTotalLitersForFills() {
        double sum = 0.0;
        for (DistillateFill distillateFill: distillateFills) {
            sum += distillateFill.getAmountOfDistillateInLiters();
        }
        return sum;
    }
    public LocalDate getFirstTimeOfFill() {
        return firstTimeOfFill;
    }
    public ArrayList<FillOnCask> getFillOnCasks() {
        return fillOnCasks;
    }
    public List<DistillateFill> getDistillateFills() {
        return distillateFills;
    }

    // ---------------------------------------------------------------------
    /** Adds & removes */
    // ---------------------------------------------------------------------

    public void addFillOnCask(FillOnCask fillOnCask) {
        fillOnCasks.add(fillOnCask);
    }
    public void addDistillateFill(DistillateFill distillateFill) {
        distillateFills.add(distillateFill);
    }

    @Override
    public String toString() {
        int years = Period.between(firstTimeOfFill, LocalDate.now()).getYears();
        int months = Period.between(firstTimeOfFill, LocalDate.now()).getMonths();
        int days = Period.between(firstTimeOfFill, LocalDate.now()).getDays();
        String timeOfAging = "";
        if (years > 0) {
            timeOfAging += years + " År, ";
        }
        if (months > 0) {
            timeOfAging += months + " Måneder, ";
        }
        if (days > 0) {
            timeOfAging += days + " Dage";
        }
        return timeOfAging;
    }
}
