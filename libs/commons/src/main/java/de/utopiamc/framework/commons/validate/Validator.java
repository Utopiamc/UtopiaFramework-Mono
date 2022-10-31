package de.utopiamc.framework.commons.validate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    public static void requireNonNull(Object object) {
        requireNonNull(object, "Object is null!");
    }

    public static void requireNonNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }

    public static void requireNull(Object object) {
        requireNull(object, "Object is not null!");
    }

    public static void requireNull(Object object, String message) {
        if (object != null)
            throw new IllegalArgumentException(message);
    }

    public static void requireTrue(boolean bool) {
        requireTrue(bool, "Should be true!");
    }

    public static void requireTrue(boolean bool, String message) {
        if (!bool)
            throw new IllegalArgumentException(message);
    }

    public static void requireFalse(boolean bool) {
        requireFalse(bool, "Should be false!");
    }

    public static void requireFalse(boolean bool, String message) {
        if (bool)
            throw new IllegalArgumentException(message);
    }

    public static void requirePositive(int number) {
        requirePositive(number, "Should be positive!");
    }

    public static void requirePositive(int number, String message) {
        if (number <= 0)
            throw new IllegalArgumentException(message);
    }

    public static void requirePositive(double number) {
        requirePositive(number, "Should be positive!");
    }

    public static void requirePositive(double number, String message) {
        if (number <= 0)
            throw new IllegalArgumentException(message);
    }

    public static void requirePositive(float number) {
        requirePositive(number, "Should be positive!");
    }

    public static void requirePositive(float number, String message) {
        if (number <= 0)
            throw new IllegalArgumentException(message);
    }

    public static void requireNegative(int number) {
        requireNegative(number, "Should be negative!");
    }

    public static void requireNegative(int number, String message) {
        if (number >= 0)
            throw new IllegalArgumentException(message);
    }

}
