package model;

import java.io.Serializable;

public class WhiskyBottle implements Serializable {
    private int bottleId;
    private int centiliterCapacity;
    private Whisky whisky;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public WhiskyBottle(int bottleId, int centiliterCapacity, Whisky whisky) {
        this.bottleId = bottleId;
        this.centiliterCapacity = centiliterCapacity;
        this.whisky = whisky;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public Whisky getWhisky() {
        return whisky;
    }
    public void setWhisky(Whisky whisky) {
        this.whisky = whisky;
    }
}
