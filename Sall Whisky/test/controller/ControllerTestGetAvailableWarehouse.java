package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.ListStorage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTestGetAvailableWarehouse {

    private CaskSupplier cs1;
    private Cask c1;
    private Warehouse wh1;
    private Warehouse wh2;
    private Rack r1;
    private Rack r2;
    private Shelf sh1;
    private Shelf sh2;
    private Position po1;
    private Position po2;
    private Storage storage;

    @BeforeEach
    void setup() {
        storage = new ListStorage();
        Controller.setStorage(storage);
        cs1 = Controller.createCaskSupplier("Oliver", "Avenue Paris 45", "Frankrig", "FR12345678910");

        wh1 = Controller.createWarehouse( "Lagervej 1");
        r1 = Controller.createRack(wh1);
        sh1 = Controller.createShelf(r1);
        po1 = Controller.createPosition(sh1, 50);
        c1 = Controller.createCask("Frankrig", 30, "Whisky", po1, cs1);

        wh2 = Controller.createWarehouse("Lagervej 2");
        r2 = Controller.createRack(wh2);
        sh2 = Controller.createShelf(r2);
        po2 = Controller.createPosition(sh2, 30);
    }
    @Test
    void TestCase1() {
        // Arrange
        Cask c1 = new Cask("Frankrig", 20, "Vin");
        // Act
        List<Warehouse> warehouses = new ArrayList<>(Controller.getAvailableWarehouses(c1));

        // Assert
        assertTrue(warehouses.contains(wh2));
        assertTrue(warehouses.contains(wh1));
    }

    @Test
    void TestCase2() {
        // Arrange
        Cask c1 = new Cask("Frankrig", 30, "Vin");
        // Act
        List<Warehouse> warehouses = new ArrayList<>(Controller.getAvailableWarehouses(c1));

        // Assert
        assertTrue(warehouses.contains(wh2));
        assertFalse(warehouses.contains(wh1));
    }

    @Test
    void TestCase3() {
        // Arrange
        Cask c1 = new Cask("Frankrig", 40, "Vin");
        // Act
        List<Warehouse> warehouses = new ArrayList<>(Controller.getAvailableWarehouses(c1));

        // Assert
        assertFalse(warehouses.contains(wh2));
        assertFalse(warehouses.contains(wh1));
        assertEquals(0, Controller.getAvailableWarehouses(c1).size());
    }
}