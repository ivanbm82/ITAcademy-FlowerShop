package org.flowershop.domain;

import org.flowershop.domain.products.Flower;
import org.flowershop.exceptions.NegativeValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FlowerTest  {

    @DisplayName("Adding new stock")
    @Test
    public void updateFlowerStock() {
        // arrange
        Flower flower = new Flower("F001", "Tulipán", 2.5, "white");

        // act
        try {
            flower.setStock(5);
            flower.updateStock(10);
        } catch (NegativeValueException e) {
            System.out.println(e.getMessage());
        }

        // assert
        assertEquals("F001", flower.getRef());
        assertEquals(15, flower.getStock());
    }

    @DisplayName("Addition of a negative stock value")
    @Test
    public void setFlowerStockWithNegativeValue() {
        Flower flower = new Flower("F001", "Tulipán", 2.5, "white");

        // assert
        assertEquals("F001", flower.getRef());
        try {
            flower.setStock(-5);
            // The exception must be thrown
            fail("Expected NegativeValueException to be thrown.");
        } catch (NegativeValueException e) {
            assertEquals("Stock cannot be negative.", e.getMessage());
            assertEquals(0, flower.getStock());
        }
    }

    @DisplayName("Updating price with a negative value.")
    @Test
    public void updatePriceWithNegativeValue() {
        Flower flower = new Flower("F001", "Tulipán", 2.50, "white");

        // assert
        assertEquals("F001", flower.getRef());
        try {
            flower.setPrice(-5);
            // The exception must be thrown
            fail("Expected NegativeValueException to be thrown.");
        } catch (NegativeValueException e) {
            assertEquals("The price cannot be negative.", e.getMessage());
            assertEquals(2.50, flower.getPrice());
        }
    }

    @DisplayName("Flower constructor takes the price in absolute value.")
    @Test
    public void settingTreeWithNegativePrice() {
        Flower flower = new Flower("F001", "Tulipán", -2.50, "white");

        // assert
        assertEquals(2.50, flower.getPrice());
    }

}
