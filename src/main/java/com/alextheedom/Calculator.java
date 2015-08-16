package com.alextheedom;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by atheedom on 25/07/15.
 */
public class Calculator {

    private BigDecimal runningValue;
    private ZeroMode zeroMode = ZeroMode.NORMAL;

    // Determines the way to treat zeros in multiplications and divisions
    // NORMAL mode: multiply by 0 results in 0, divide by zero results in exception
    // SPECIAL mode: multiply by 0 results in the passed in operand becoming the result, as if it were multiplied by 1,
    // likewise divide by zero results in the passed in operand becoming the result, as if it were divided by 1.
    public enum ZeroMode {
        NORMAL, SPECIAL
    }


    public Calculator(double value) {
        this.runningValue = new BigDecimal(value);
    }

    public Calculator(BigDecimal value) {
        this.runningValue = value;
    }


    public Calculator(ZeroMode zeroMode, BigDecimal value) {
        this.zeroMode = zeroMode;
        this.runningValue = value;
    }

    public Calculator(ZeroMode zeroMode) {
        this.zeroMode = zeroMode;
    }

    public Calculator setMode(ZeroMode zeroMode){
        this.zeroMode = zeroMode;
        return this;
    }

    public BigDecimal bigDecimalValue() {
        return bigDecimalValue(2, BigDecimal.ROUND_HALF_DOWN);
    }


    public BigDecimal bigDecimalValue(int newScale, int roundingMode) {
        if (runningValue == null) {
            return BigDecimal.ZERO.setScale(newScale, roundingMode);
        } else {
            return this.runningValue.setScale(newScale, roundingMode);
        }
    }

    public Optional<BigDecimal> optionalValue() {
        return Optional.ofNullable(bigDecimalValue());
    }

    public Double resultAsDouble() {
        return this.bigDecimalValue().doubleValue();
    }

    // BigDecimal data types
    public Calculator add(BigDecimal value) {
        if (value != null) {
            if (isZero(runningValue)) {
                this.runningValue = value;
            } else {
                this.runningValue = runningValue.add(value);
            }
        }
        return this;
    }

    public Calculator subtract(BigDecimal value) {
        if (value != null) {
            if (isZero(runningValue)) {
                this.runningValue = value;
            } else {
                this.runningValue = runningValue.subtract(value);
            }
        }
        return this;
    }

    public Calculator multiply(BigDecimal value) {
        if (zeroMode.equals(ZeroMode.SPECIAL) && isZero(value)) {
            return this;
        } else {
            if (isZero(runningValue)) {
                this.runningValue = value;
            } else {
                this.runningValue = runningValue.multiply(value);
            }
        }
        return this;
    }


    public Calculator divide(BigDecimal value) {
        if (zeroMode.equals(ZeroMode.SPECIAL) && isZero(value)) {
            return this;
        } else {
            if (isZero(runningValue)) {
                this.runningValue = value;
            } else {
                // TODO: set rounding arguments for divide
                this.runningValue = runningValue.divide(value);
            }
        }
        return this;
    }


    // Optional wrapping Decimal
    public Calculator add(Optional<BigDecimal> value) {
        if (value.isPresent()) {
            if (isZero(runningValue)) {
                this.runningValue = value.get();
            } else {
                this.runningValue = runningValue.add(value.get());
            }
        }
        return this;
    }

    public Calculator subtract(Optional<BigDecimal> value) {
        if (value.isPresent()) {
            if (runningValue == null) {
                this.runningValue = value.get();
            } else {
                this.runningValue = runningValue.subtract(value.get());
            }
        }
        return this;
    }

    public Calculator multiply(Optional<BigDecimal> value) {
        if (zeroMode.equals(ZeroMode.SPECIAL) && !isZero(value.get())) {
            return this;
        } else {
            if (runningValue == null) {
                this.runningValue = value.get();
            } else {
                this.runningValue = runningValue.multiply(value.get());
            }
        }
        return this;
    }


    public Calculator divide(Optional<BigDecimal> value) {
        if (!isZero(value.get())) {
            if (zeroMode.equals(ZeroMode.SPECIAL) && !isZero(value.get())) {
                return this;
            } else {
                if (runningValue == null) {
                    this.runningValue = value.get();
                } else {
                    // TODO: set rounding arguments for divide
                    this.runningValue = runningValue.divide(value.get());
                }
            }
        }
        return this;
    }


    // Compound: Multiply
    public Calculator multiply(Optional<BigDecimal>... values) {
        for (Optional<BigDecimal> value : values) {
            if (!isZero(value.get())) {
                if (zeroMode.equals(ZeroMode.SPECIAL) && !isZero(value.get())) {
                    return this;
                } else {
                    if (runningValue == null) {
                        this.runningValue = value.get();
                    } else {
                        this.runningValue = runningValue.multiply(value.get());
                    }
                }
            }
        }
        return this;
    }

    public Calculator multiply(BigDecimal... values) {
        for (BigDecimal value : values) {
            if (zeroMode.equals(ZeroMode.SPECIAL) && !isZero(value)) {
                return this;
            } else {
                if (runningValue == null) {
                    this.runningValue = value;
                } else {
                    this.runningValue = runningValue.multiply(value);
                }
            }
        }
        return this;
    }


    // Compound: Divide
    public Calculator divide(Optional<BigDecimal>... values) {
        for (Optional<BigDecimal> value : values) {
            if (!isZero(value.get())) {
                if (zeroMode.equals(ZeroMode.SPECIAL) && isZero(runningValue)) {
                    this.runningValue = value.get();
                } else {
                    if (runningValue == null) {
                        this.runningValue = value.get();
                    } else {
                        // TODO: set rounding arguments for divide
                        this.runningValue = runningValue.divide(value.get());
                    }
                }
            }
        }
        return this;
    }

    public Calculator divide(BigDecimal... values) {
        for (BigDecimal value : values) {
            if (value != null && value.compareTo(BigDecimal.ZERO) != 0) {
                if (zeroMode.equals(ZeroMode.SPECIAL) && isZero(runningValue)) {
                    this.runningValue = value;
                } else {
                    if (runningValue == null) {
                        this.runningValue = value;
                    } else {
                        // TODO: set rounding arguments for divide
                        this.runningValue = runningValue.divide(value);
                    }
                }
            }
        }
        return this;
    }


    private boolean isZero(BigDecimal value){
        return (value == null || value.compareTo(BigDecimal.ZERO) == 0);
    }

}

