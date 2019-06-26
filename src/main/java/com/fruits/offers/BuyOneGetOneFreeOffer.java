package com.fruits.offers;

import java.math.BigDecimal;

public class BuyOneGetOneFreeOffer implements Offer {

    @Override
    public BigDecimal calculatePrice(long itemCount, BigDecimal unitPrice) {
        long chargeableItemCount = itemCount - (itemCount / 2);
        return unitPrice.multiply(new BigDecimal(chargeableItemCount));
    }
}
