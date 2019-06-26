package com.fruits.offers;

import java.math.BigDecimal;

public class ThreeForTwoOffer implements Offer {

    @Override
    public BigDecimal calculatePrice(long itemCount, BigDecimal unitPrice) {
        long chargeableItemCount = itemCount - (itemCount / 3);
        return unitPrice.multiply(new BigDecimal(chargeableItemCount));
    }
}
