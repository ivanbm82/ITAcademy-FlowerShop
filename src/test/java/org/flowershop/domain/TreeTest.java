package org.flowershop.domain;

import org.flowershop.domain.products.Tree;
import org.flowershop.exceptions.NegativeValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {

    @DisplayName("Adding new stock")
    @Test
    public void updateTreeStock() {
        // arrange
        Tree tree = new Tree("T001", "Abeto", 10.0, 1.8f);

        // act
        try {
            tree.setStock(5);
            tree.updateStock(10);
        } catch (NegativeValueException e) {
            System.out.println(e.getMessage());
        }

        // assert
        assertEquals("T001", tree.getRef());
        assertEquals(15, tree.getStock());
    }

    @DisplayName("Addition of a negative stock value")
    @Test
    public void setTreeStockWithNegativeValue() {
        Tree tree = new Tree("T001", "Abeto", 10.0, 1.8f);

        // assert
        assertEquals("T001", tree.getRef());
        try {
            tree.setStock(-5);
            // The exception must be thrown
            fail("Expected NegativeValueException to be thrown.");
        } catch (NegativeValueException e) {
            assertEquals("Stock cannot be negative.", e.getMessage());
            assertEquals(0, tree.getStock());
        }
    }

    @DisplayName("Updating price with a negative value.")
    @Test
    public void updatePriceWithNegativeValue() {
        Tree tree = new Tree("T001", "Abeto", 10.0, 1.8f);

        // assert
        assertEquals("T001", tree.getRef());
        try {
            tree.setPrice(-5);
            // The exception must be thrown
            fail("Expected NegativeValueException to be thrown.");
        } catch (NegativeValueException e) {
            assertEquals("The price cannot be negative.", e.getMessage());
            assertEquals(10.0, tree.getPrice());
        }
    }

    @DisplayName("Tree constructor takes the price in absolute value.")
    @Test
    public void settingTreeWithNegativePrice() {
        Tree tree = new Tree("T001", "Abeto", -10.20, 1.8f);

        // assert
        assertEquals(10.20, tree.getPrice());
    }

}
