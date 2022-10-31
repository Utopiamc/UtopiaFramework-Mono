package de.utopiamc.framework.commons.validate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidatorTests {

    @Test
    void requireNonNull() {
        // given
        Object nullable = null;

        // when
        // then
        assertThatThrownBy(() -> Validator.requireNonNull(nullable));
    }

    @Test
    void requireNull() {
        // given
        Object notNull = new Object();

        // when
        // then
        assertThatThrownBy(() -> Validator.requireNull(notNull));
    }

    @Test
    void requireTrue() {
        // given
        boolean bool = false;

        // when
        // then
        assertThatThrownBy(() -> Validator.requireTrue(bool));
    }

    @Test
    void requireFalse() {
        // given
        boolean bool = true;

        // when
        // then
        assertThatThrownBy(() -> Validator.requireFalse(bool));
    }

    @Test
    void requirePositiveInt() {
        int number = -1;

        // when
        // then
        assertThatThrownBy(() -> Validator.requirePositive(number));
    }

    @Test
    void testRequirePositiveDouble() {
        double number = -1;

        // when
        // then
        assertThatThrownBy(() -> Validator.requirePositive(number));
    }

    @Test
    void testRequirePositiveFloat() {
        float number = -1;

        // when
        // then
        assertThatThrownBy(() -> Validator.requirePositive(number));
    }

    @Test
    void requireNegative() {
        int number = 1;

        // when
        // then
        assertThatThrownBy(() -> Validator.requireNegative(number));
    }

}