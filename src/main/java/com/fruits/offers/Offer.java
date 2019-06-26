package com.fruits.offers;

import java.math.BigDecimal;

public interface Offer {

    BigDecimal calculatePrice(long itemCount, BigDecimal unitPrice);
}
