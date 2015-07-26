package com.alextheedom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.alextheedom.Calculator.ZeroMode.NORMAL;
import static com.alextheedom.Calculator.ZeroMode.SPECIAL;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by atheedom on 25/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    private static BigDecimal FIVE = new BigDecimal(5);
    private static BigDecimal NINE = new BigDecimal(9);
    private static BigDecimal TWENTY = new BigDecimal(20);

    // SPECIAL MODE
    @Test
    public void MultiplyShouldIgnoreZeroInSpecialMode() {
        // arrange
        Calculator calculator = new Calculator(SPECIAL, BigDecimal.TEN);
        // act
        calculator.multiply(BigDecimal.ZERO);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void MultiplyShouldReturnOperandInSpecialMode() {
        // arrange  // act
        Calculator calculator = new Calculator(SPECIAL, BigDecimal.TEN);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void AddShouldAddNumbersInSpecialMode() {
        // arrange
        Calculator calculator = new Calculator(SPECIAL, BigDecimal.TEN);
        // act
        calculator.add(BigDecimal.TEN);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(TWENTY);
    }

    @Test
    public void SubtractShouldSubtractNumbersInSpecialMode() {
        // arrange
        Calculator calculator = new Calculator(SPECIAL, BigDecimal.TEN);
        // act
        calculator.subtract(BigDecimal.ONE);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(NINE);
    }

    @Test()
    public void DivideShouldIgnoreZeroInSpecialMode() {
        // arrange
        Calculator calculator = new Calculator(SPECIAL, BigDecimal.TEN);
        // act
        calculator.divide(BigDecimal.ZERO);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(BigDecimal.TEN);
    }

    // NORMAL MODE
    @Test
    public void MultiplyShouldAccountForZeroInNormalMode() {
        // arrange
        Calculator calculator = new Calculator(NORMAL, BigDecimal.TEN);
        // act
        calculator.multiply(BigDecimal.ZERO);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void MultiplyShouldReturnOperandInNormalMode() {
        // arrange // act
        Calculator calculator = new Calculator(NORMAL, BigDecimal.TEN);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void AddShouldAddNumbersInNormalMode() {
        // arrange
        Calculator calculator = new Calculator(NORMAL, BigDecimal.TEN);
        // act
        calculator.add(BigDecimal.TEN);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(TWENTY);
    }

    @Test
    public void SubtractShouldSubtractNumbersInNormalMode() {
        // arrange
        Calculator calculator = new Calculator(NORMAL, BigDecimal.TEN);
        // act
        calculator.subtract(BigDecimal.ONE);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(NINE);
    }

    @Test(expected = ArithmeticException.class)
    public void DivideShouldErrorWhenDivideByZeroInNormalMode() {
        // arrange
        Calculator calculator = new Calculator(NORMAL, BigDecimal.TEN);
        // act
        calculator.divide(BigDecimal.ZERO);
        // assert
        assertThat(calculator.bigDecimalValue(0, BigDecimal.ROUND_HALF_DOWN)).isEqualTo(BigDecimal.TEN);
    }


    @Test
    public void shouldRoundDownTo2DecimalPlace() {
        // arrange // act
        Calculator calculator = new Calculator(new BigDecimal(0.33333));
        // assert
        assertThat(calculator.bigDecimalValue()).isEqualTo(new BigDecimal(0.33).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    }

    @Test
    public void shouldNotRoundDuringCalculation() {
        // arrange
        Calculator calculator = new Calculator(new BigDecimal(0.333));
        // act
        calculator.add(new BigDecimal(0.333)).add(new BigDecimal(0.334));
        // assert
        assertThat(calculator.bigDecimalValue()).isEqualTo(new BigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    }

}

