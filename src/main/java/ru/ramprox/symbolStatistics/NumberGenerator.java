package ru.ramprox.symbolStatistics;

import java.math.BigInteger;
import java.util.Random;

public class NumberGenerator {

    private final Random random;

    public NumberGenerator(Random random) {
        this.random = random;
    }

    /**
     * Генерирует строковое представление случайного числа в диапазоне [-upperBound, +upperBound]
     * @param upperBound - строковое представление верхней границы
     * @return строковое представление случайного числа
     */
    public String generate(String upperBound) {
        BigInteger upperBoundValue = new BigInteger(upperBound);
        if(upperBoundValue.equals(BigInteger.ZERO)) {
            return BigInteger.ZERO.toString();
        }
        BigInteger upperBoundMulti2 = upperBoundValue.multiply(BigInteger.TWO);
        int bitLength = upperBoundMulti2.bitLength();
        BigInteger result;
        do {
            result = new BigInteger(bitLength, random);
        } while (result.compareTo(upperBoundMulti2) > 0);
        return result.subtract(upperBoundValue).toString();
    }

}
