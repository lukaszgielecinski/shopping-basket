package com.fruits.domain;

import com.fruits.offers.Offer;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductPrice {

    private BigDecimal unitPrice;
    private Offer offer;

    public ProductPrice(BigDecimal unitPrice, Offer offer) {
        this.unitPrice = unitPrice;
        this.offer = offer;
    }

    public ProductPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Optional<Offer> getOffer() {
        return Optional.ofNullable(offer);
    }
}
