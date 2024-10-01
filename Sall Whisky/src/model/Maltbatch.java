package model;

import java.io.Serializable;

public class Maltbatch implements Serializable {
    private String name;
    private String description;
    private Grain grain;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public Maltbatch(String name, String description, Grain grain) {
        this.name = name;
        this.description = description;
        this.grain = grain;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Grain getGrain() {
        return grain;
    }

    @Override
    public String toString() {
        return name + " | " + description;
    }
}
