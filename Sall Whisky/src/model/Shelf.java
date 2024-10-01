package model;

import controller.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shelf implements Observer, Serializable {
    private int shelfId;
    private boolean isFilled = true;
    private List<Position> positions = new ArrayList<>();
    private Rack rack;

    // ---------------------------------------------------------------------
    /** Constructors */
    // ---------------------------------------------------------------------

    public Shelf(int shelfId, Rack rack) {
        this.shelfId = shelfId;
        this.rack = rack;

    }

    // ---------------------------------------------------------------------
    /** Methods */
    // ---------------------------------------------------------------------

    /** Checks if all the positions on the shelf are full, if so, set isFilled to true. */
    @Override
    public void update() {
        if (getAvailablePositions().isEmpty()) {
            isFilled = true;
        } else {
            isFilled = false;
        }
    }

    // ---------------------------------------------------------------------
    /** Getters & setters */
    // ---------------------------------------------------------------------

    /** Return a list of all the positions on the shelf that are not fully filled. */
    public List<Position> getAvailablePositions() {
        List<Position> availablePositions = new ArrayList<>();
        for (Position position : positions) {
            if (!position.isFilled())
                availablePositions.add(position);
        }
        return availablePositions;
    }
    public int getShelfId() {
        return shelfId;
    }
    public Rack getRack() {
        return rack;
    }
    public boolean isFilled() {
        return isFilled;
    }
    public List<Position> getPositions() {
        return positions;
    }

    // ---------------------------------------------------------------------
    /** Adds & removes */
    // ---------------------------------------------------------------------

    public void addPosition(Position position) {
        positions.add(position);
    }
    public void removePosition(Position position) {
        positions.remove(position);
    }

    @Override
    public String toString() {
        return "" + shelfId;
    }
}
