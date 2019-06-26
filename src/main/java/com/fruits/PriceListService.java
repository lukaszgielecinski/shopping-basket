package com.fruits;

import com.fruits.domain.ProductPrice;
import com.fruits.offers.BuyOneGetOneFreeOffer;
import com.fruits.offers.ThreeForTwoOffer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

class PriceListService {

    private static Map<String, ProductPrice> FIXED_PRODUCT_PRICE_LIST = Map.of(
            "Apple", new ProductPrice(bigDecimalPrice(0.35)),
            "Banana", new ProductPrice(bigDecimalPrice(0.20)),
            "Melon", new ProductPrice(bigDecimalPrice(0.50), new BuyOneGetOneFreeOffer()),
            "Lime", new ProductPrice(bigDecimalPrice(0.15), new ThreeForTwoOffer()));

    private static BigDecimal bigDecimalPrice(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_EVEN);
    }

    Map<String, ProductPrice> getProductPriceList() {
        return FIXED_PRODUCT_PRICE_LIST;
    }
}
