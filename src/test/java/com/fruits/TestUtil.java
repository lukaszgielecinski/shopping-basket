package com.fruits;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestUtil {
    public static BigDecimal bigDecimalPrice(double v) {
        return new BigDecimal(v).setScale(2, RoundingMode.HALF_EVEN);
    }
}
