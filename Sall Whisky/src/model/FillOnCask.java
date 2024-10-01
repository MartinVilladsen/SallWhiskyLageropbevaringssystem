package model;

import java.io.Serializable;
import java.time.LocalDate;

public class FillOnCask implements Serializable {
    private LocalDate timeFill;
    private TapFromDistillate tapFromDistillate;
    private Cask cask;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public FillOnCask(LocalDate timeFill, TapFromDistillate tapFromDistillate, Cask cask) {
        this.timeFill = timeFill;
        this.tapFromDistillate = tapFromDistillate;
        this.cask = cask;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public LocalDate getTimeFill() {
        return timeFill;
    }
    public TapFromDistillate getTapFromDistillate() {
        return tapFromDistillate;
    }
    public Cask getCask() {
        return cask;
    }
    public void setTimeFill(LocalDate timeFill) {
        this.timeFill = timeFill;
    }
    public void setCask(Cask cask) {
        this.cask = cask;
    }

    @Override
    public String toString() {
        return "FillOnCask{" +
                "timeFill=" + timeFill +
                ", tapFromDistillate=" + tapFromDistillate +
                ", cask=" + cask +
                '}';
    }
}
