package com.fruits;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BasketPriceServiceTest {

    private BasketPriceService service = new BasketPriceService();

    @Test
    public void should_return_0_if_throw_exception_if_null_basket() {
        assertThatThrownBy(

                // when
                () -> service.calculatePrice(null))

                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Received null basket");
    }

    @Test
    public void should_return_0_if_empty_basket() {
        // when
        BigDecimal basketPrice = service.calculatePrice(Collections.emptyList());

        // then
        assertThat(basketPrice).isEqualTo(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    public void should_throw_exception_if_unknown_product_in_the_basket() {
        assertThatThrownBy(

                // when
                () -> service.calculatePrice(List.of("Orange")))

                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Found unknown products in the basket: [Orange]");
    }

    @Test
    public void should_throw_exception_if_unknown_and_known_products_in_the_basket() {
        assertThatThrownBy(

                // when
                () -> service.calculatePrice(List.of("Orange", "Clementine", "Lime")))

                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("Found unknown products in the basket: \\[\\w+,\\w+]")
                .hasMessageMatching("Found unknown products in the basket: \\[.*Orange.*]")
                .hasMessageMatching("Found unknown products in the basket: \\[.*Clementine.*]")
                .hasMessageNotContaining("Lime");
    }

    @Test
    public void should_return_price_of_single_product() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Apple"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(0.35));
    }

    @Test
    public void should_return_price_of_different_single_products() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Apple", "Banana", "Melon", "Lime"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(1.20));
    }

    @Test
    public void should_return_price_of_multiple_occurrences_of_same_product() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Banana", "Banana"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(0.40));
    }

    @Test
    public void should_return_price_of_multiple_occurrences_of_different_products() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Banana", "Lime", "Banana", "Melon", "Lime"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(1.20));
    }

    @Test
    public void should_return_apply_offer_for_single_product() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Melon", "Melon"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(0.50));
    }

    @Test
    public void should_return_apply_offer_for_multiple_products() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Melon", "Lime", "Lime", "Melon", "Lime"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(0.80));
    }

    @Test
    public void should_return_price_of_products() {
        // when
        BigDecimal basketPrice = service.calculatePrice(List.of("Melon", "Lime", "Lime", "Melon", "Lime", "Lime", "Apple", "Banana", "Apple"));

        // then
        assertThat(basketPrice).isEqualTo(TestUtil.bigDecimalPrice(1.85));
    }
}