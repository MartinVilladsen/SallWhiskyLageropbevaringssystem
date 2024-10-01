package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TapFromDistillateTestGetTotalLitersForFills {
    private TapFromDistillate tapFromDistillate;
    private Distillate distillate1;
    private Distillate distillate2;
    private DistillateFill df1;
    private DistillateFill df2;

    // ---------------------------------------------------------------------
    /** setUp */
    // ---------------------------------------------------------------------

    @BeforeEach
    void setup() {
        tapFromDistillate = new TapFromDistillate(LocalDate.now(), null);
        distillate1 = new Distillate("nmn12", 9, 67.4, 250, null, null, "");
        distillate2 = new Distillate("nmn23", 8, 40, 150, null,null, "");
        df1 = new DistillateFill(20, distillate1);
        df2 = new DistillateFill(13.6, distillate2);
    }

    // ---------------------------------------------------------------------
    /** Testcases */
    // ---------------------------------------------------------------------

    /** TC1: Without DistillateFill */
    @Test
    void testCase1() {
        // Arrange

        // Act
        double actualResult = tapFromDistillate.getTotalLitersForFills();

        // Assert
        assertEquals(0.0, actualResult, 0.001);
    }

    /** TC2: With 1 DistillateFill */
    @Test
    void testCase2() {
        // Arrange
        tapFromDistillate.addDistillateFill(df1);
        // Act
        double actualResult = tapFromDistillate.getTotalLitersForFills();

        // Assert
        assertEquals(20, actualResult, 0.001);

    }

    /** TC3: with 2 DistillateFills */
    @Test
    void testCast3() {
        // Arrange
        tapFromDistillate.addDistillateFill(df1);
        tapFromDistillate.addDistillateFill(df2);

        // Act
        double actualResult = tapFromDistillate.getTotalLitersForFills();

        // Assert
        assertEquals(33.6, actualResult, 0.001);
    }
}