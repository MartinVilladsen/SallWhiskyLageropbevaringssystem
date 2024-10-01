package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.Control;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTestCreateTapFromDistillate {

    private Cask cask;
    private Distillate distillate;
    private DistillateFill distillateFill;
    private ArrayList<DistillateFill> distillateFills = new ArrayList<>();

    // ---------------------------------------------------------------------
    /** setUp */
    // ---------------------------------------------------------------------

    @BeforeEach
    void setup() {
        cask = new Cask("Frankrig", 50, "Whisky");
        distillate = new Distillate("nmn23", 8, 40, 150, null, null, "");
        distillateFills = new ArrayList<>();
    }

    // ---------------------------------------------------------------------
    /** Testcases */
    // ---------------------------------------------------------------------

    /** TC1: TimeOfFill 01-12-2023, fill: 49 liters (max caskSize 50 liters) */
    @Test
    void TestCase1() {
        // Arrange
        distillateFill = new DistillateFill(49, distillate);
        distillateFills.add(distillateFill);
        TapFromDistillate tapFromDistillate = Controller.createTapFromDistillate(LocalDate.of(2023, 12, 1), cask, distillateFills);

        // Act
        distillateFill.setFillOnCask(tapFromDistillate);

        // Assert
        assertTrue(tapFromDistillate.getDistillateFills().contains(distillateFill));
        assertTrue(distillateFill.getFillOnCask().equals(tapFromDistillate));
    }

    /** TC2: TimeOfFill 04-12-2024, fill: 49 liters (max caskSize 50 liters) */
    @Test
    void TestCase2() {
        // Arrange
        distillateFill = new DistillateFill(49, distillate);
        distillateFills.add(distillateFill);

        // Asserts
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createTapFromDistillate(LocalDate.of(2024, 12, 4), cask, distillateFills);
        });
    }

    /** TC3: TimeOfFill 03-12-2023, fill: 50 liters (max caskSize 50 liters) */
    @Test
    void TestCase3() {
        // Arrange
        distillateFill = new DistillateFill(50, distillate);
        distillateFills.add(distillateFill);
        TapFromDistillate tapFromDistillate = Controller.createTapFromDistillate(LocalDate.of(2023, 12, 3), cask, distillateFills);

        // Act
        distillateFill.setFillOnCask(tapFromDistillate);

        // Assert
        assertTrue(tapFromDistillate.getDistillateFills().contains(distillateFill));
        assertTrue(distillateFill.getFillOnCask().equals(tapFromDistillate));
    }

    /** TC4: TimeOfFill 01-12-2023, fill: 51 liters (max caskSize 50 liters) */
    @Test
    void TestCase4() {
        // Arrange
        distillateFill = new DistillateFill(51, distillate);
        distillateFills.add(distillateFill);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createTapFromDistillate(LocalDate.of(2023, 12, 1), cask, distillateFills);
        });
    }
}