package model;

import java.io.Serializable;

public class Field implements Serializable {
    private String name;
    private String description;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public Field(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " | " + description;
    }
}
