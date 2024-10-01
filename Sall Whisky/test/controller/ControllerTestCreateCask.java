package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.ListStorage;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTestCreateCask {

    private Cask cask;
    private String countryOfOrigin;
    private double sizeInLiters;
    private String previousContent;
    private Warehouse warehouse;
    private Rack rack;
    private Shelf shelf;
    private Position position;
    private CaskSupplier supplier;
    private Storage storage;

    // ---------------------------------------------------------------------
    /** setUp */
    // ---------------------------------------------------------------------

    @BeforeEach
    void setUp() {
        storage = new ListStorage();
        Controller.setStorage(storage);
        countryOfOrigin = "Frankrig";
        warehouse = new Warehouse(1, "Testfirma");
        rack = new Rack(1, warehouse);
        shelf = new Shelf(1, rack);
        position = new Position(15,50, shelf);
        supplier = new CaskSupplier("Cask123", "ABC123", "Frankrig", "2");
    }

    // ---------------------------------------------------------------------
    /** Testcases */
    // ---------------------------------------------------------------------

    /** TC1: SizeInLiters 50, prevC Whisky */
    @Test
    void testCase1() {
        // Arrange
        sizeInLiters = 50;
        previousContent = "Whisky";
        // Act
        cask = Controller.createCask(countryOfOrigin, sizeInLiters, previousContent, position, supplier);
        // Assert
        assertTrue(cask.getPosition().equals(position));
        assertTrue(position.getCasks().contains(cask));
        assertTrue(cask.getSupplier().equals(supplier));
    }

    /** SizeInLiters 50, prevC null */
    @Test
    void testCase2() {
        // Arrange
        sizeInLiters = 50;
        previousContent = "";
        // Act
        cask = Controller.createCask(countryOfOrigin, sizeInLiters, previousContent, position, supplier);
        // Assert
        assertTrue(cask.getPosition().equals(position));
        assertTrue(position.getCasks().contains(cask));
        assertTrue(cask.getSupplier().equals(supplier));
    }

    /** SizeInLiters 0, prevC Whisky */
    @Test
    void testCase3() {
        // Arrange
        sizeInLiters = 0;
        previousContent = "Whisky";
        // Act

         //----------NULL---------

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createCask(countryOfOrigin, sizeInLiters, previousContent, position, supplier);
        });
    }

    @Test
    void testCase4() {
        Warehouse wh1 = Controller.createWarehouse("idk");
        Rack r1 = Controller.createRack(wh1);
        Shelf sh1 = Controller.createShelf(r1);
        Position p1 = Controller.createPosition(sh1, 50);
        Position p2 = Controller.createPosition(sh1, 100);

        Cask cask = new Cask("Idk", 40, "");

        System.out.println(Controller.getAvailableWarehouses(cask));
    }
}