package org.flowershop.exceptions;

public class NegativeValueException extends Exception {

    private static String message = "The value must not be negative.";

    public NegativeValueException() {
        this(message);
    }

    public NegativeValueException(String message) {
        super(message);
    }

}
