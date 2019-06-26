package com.fruits.offers;

import org.junit.Test;

import java.math.BigDecimal;

import static com.fruits.TestUtil.bigDecimalPrice;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreeForTwoOfferTest {

    private ThreeForTwoOffer offer = new ThreeForTwoOffer();

    @Test
    public void should_return_unit_price_if_single_unit() {
        // given
        BigDecimal randomUnitPrice = bigDecimalPrice(Math.random());

        // when
        BigDecimal offerPrice = offer.calculatePrice(1, randomUnitPrice);

        // then
        assertThat(offerPrice).isEqualTo(randomUnitPrice);
    }

    @Test
    public void should_return_price_of_two_units_if_two_items() {
        // given
        BigDecimal unitPrice = bigDecimalPrice(0.15);

        // when
        BigDecimal offerPrice = offer.calculatePrice(2, unitPrice);

        // then
        assertThat(offerPrice).isEqualTo(bigDecimalPrice(0.30));
    }

    @Test
    public void should_apply_offer_for_three_items() {
        // given
        BigDecimal unitPrice = bigDecimalPrice(0.35);

        // when
        BigDecimal offerPrice = offer.calculatePrice(3, unitPrice);

        // then
        assertThat(offerPrice).isEqualTo(bigDecimalPrice(0.70));
    }

    @Test
    public void should_apply_offer_for_many_items_fully_promotional() {
        // given
        BigDecimal unitPrice = bigDecimalPrice(0.25);

        // when
        BigDecimal offerPrice = offer.calculatePrice(12, unitPrice);

        // then
        assertThat(offerPrice).isEqualTo(bigDecimalPrice(2.00));
    }

    @Test
    public void should_apply_offer_for_many_items_not_fully_promotional() {
        // given
        BigDecimal unitPrice = bigDecimalPrice(0.10);

        // when
        BigDecimal offerPrice = offer.calculatePrice(11, unitPrice);

        // then
        assertThat(offerPrice).isEqualTo(bigDecimalPrice(0.80));
    }
}