package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhiskyTestCalculateAlcoholpercentage {
    private Whisky whisky;
    private Cask cask1;
    private Cask cask2;
    private WhiskyFill whiskyFill1;
    private WhiskyFill whiskyFill2;

    // ---------------------------------------------------------------------
    /** setUp */
    // ---------------------------------------------------------------------

    @BeforeEach
    void setUp() {
        cask1 = new Cask("Frankrig", 50, "Whisky");
        cask2 = new Cask("England", 70, "Whisky");
        whiskyFill1 = new WhiskyFill(50, null, LocalDate.now(), 50, cask1);
        whiskyFill2 = new WhiskyFill(70, null, LocalDate.now(), 50, cask2);
        whisky = new Whisky("Name", 0, new ArrayList<>(), "");
    }

    // ---------------------------------------------------------------------
    /** Testcases */
    // ---------------------------------------------------------------------

    /** TC1: 1 whiskyFill, no water */
    @Test
    void testCase1() {
        // Arrange
        whisky.addWhiskyFill(whiskyFill1);
        whiskyFill1.setAlcoholPercentage(52.5);

        // Act
        double actualResult = whisky.calculateAlcoholPercentage();

        // Assert
        assertEquals(52.5, actualResult);
    }

    /** TC2: 1 whiskyFill, with water */
    @Test
    void testCase2() {
        // Arrange
        whisky.addWhiskyFill(whiskyFill1);
        whisky.setWaterInLiters(12);
        whiskyFill1.setAlcoholPercentage(52.5);

        // Act
        double actualResult = whisky.calculateAlcoholPercentage();


        // Assert
        assertEquals(42.33, actualResult, 0.01);
    }

    /** TC3: 2 whiskyFills, no water */
    @Test
    void testCase3() {
        // Arrange
        whisky.addWhiskyFill(whiskyFill1);
        whisky.addWhiskyFill(whiskyFill2);
        whiskyFill1.setAlcoholPercentage(52.5);
        whiskyFill2.setAlcoholPercentage(70.4);

        // Act
        double actualResult = whisky.calculateAlcoholPercentage();

        // Assert
        assertEquals(62.94, actualResult, 0.01);
    }

    /** TC4: 2 whiskyFills, limit values, with water */
    @Test
    void testCase4() {
        // Arrange
        whisky.addWhiskyFill(whiskyFill1);
        whisky.addWhiskyFill(whiskyFill2);
        whisky.setWaterInLiters(25);
        whiskyFill1.setAlcoholPercentage(0.01);
        whiskyFill2.setAlcoholPercentage(99.99);

        // Act
        double actualResult = whisky.calculateAlcoholPercentage();

        // Assert
        assertEquals(48.27, actualResult, 0.01);
    }
}