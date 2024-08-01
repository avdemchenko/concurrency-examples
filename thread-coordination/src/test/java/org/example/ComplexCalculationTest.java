package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplexCalculationTest {

    private ComplexCalculation calculation;

    @BeforeEach
    void setUp() {
        calculation = new ComplexCalculation();
    }

    @Test
    void testCalculateResultWithPositiveNumbers() {
        // given
        BigInteger base1 = new BigInteger("10");
        BigInteger power1 = new BigInteger("2");
        BigInteger base2 = new BigInteger("5");
        BigInteger power2 = new BigInteger("3");

        // when
        BigInteger expected = new BigInteger("225");
        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);

        // then
        assertEquals(expected, result);
    }

    @Test
    void testCalculateResultWithZeroPower() {
        // given
        BigInteger base1 = new BigInteger("10");
        BigInteger power1 = new BigInteger("0");
        BigInteger base2 = new BigInteger("5");
        BigInteger power2 = new BigInteger("0");

        // when
        BigInteger expected = new BigInteger("2");
        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);

        // then
        assertEquals(expected, result);
    }

    @Test
    void testCalculateResultWithLargeNumbers() {
        // given
        BigInteger base1 = new BigInteger("999999");
        BigInteger power1 = new BigInteger("3");
        BigInteger base2 = new BigInteger("888888");
        BigInteger power2 = new BigInteger("3");

        // when
        BigInteger expected = base1.pow(3).add(base2.pow(3));
        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);

        // then
        assertEquals(expected, result);
    }

    @Test
    void testCalculateResultWithOneBaseAsOne() {
        // given
        BigInteger base1 = new BigInteger("1");
        BigInteger power1 = new BigInteger("1000");
        BigInteger base2 = new BigInteger("2");
        BigInteger power2 = new BigInteger("10");

        // when
        BigInteger expected = BigInteger.ONE.add(base2.pow(10));
        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);

        // then
        assertEquals(expected, result);
    }

    @Test
    void testCalculateResultWithBothBasesAsZero() {
        // given
        BigInteger base1 = new BigInteger("0");
        BigInteger power1 = new BigInteger("10");
        BigInteger base2 = new BigInteger("0");
        BigInteger power2 = new BigInteger("10");

        // when
        BigInteger expected = BigInteger.ZERO;
        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);

        // then
        assertEquals(expected, result);
    }

    @Test
    void testCalculateResultWithMixedPositiveAndZero() {
        // given
        BigInteger base1 = new BigInteger("0");
        BigInteger power1 = new BigInteger("10");
        BigInteger base2 = new BigInteger("5");
        BigInteger power2 = new BigInteger("2");

        // when
        BigInteger expected = new BigInteger("25");
        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);

        // then
        assertEquals(expected, result);
    }
}
