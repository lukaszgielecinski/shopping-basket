package com.fruits;

import com.fruits.domain.ProductPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class BasketPriceService {

    private PriceListService priceListService = new PriceListService();

    public BigDecimal calculatePrice(List<String> basket) {
        validateBasket(basket);

        Map<String, ProductPrice> productPriceList = priceListService.getProductPriceList();
        validateUnknownProducts(basket, productPriceList);

        return calculateBasketPrice(basket, productPriceList);
    }

    private void validateBasket(List<String> basket) {
        if (basket == null) {
            throw new IllegalArgumentException("Received null basket");
        }
    }

    private void validateUnknownProducts(List<String> basket, Map<String, ProductPrice> productPricelist) {
        Set<String> unknownProducts = basket.stream()
                .filter(product -> !productPricelist.keySet().contains(product))
                .collect(toSet());

        if (!unknownProducts.isEmpty()) {
            throw new IllegalArgumentException(String.format("Found unknown products in the basket: [%s]", String.join(",", unknownProducts)));
        }
    }

    private BigDecimal calculateBasketPrice(List<String> basket, Map<String, ProductPrice> productPriceList) {
        return basket.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(productToCount -> calculateProductPrice(productPriceList, productToCount))
                .reduce(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN), BigDecimal::add);
    }

    private BigDecimal calculateProductPrice(Map<String, ProductPrice> productPriceList, Map.Entry<String, Long> productToCount) {
        String productName = productToCount.getKey();
        Long productCount = productToCount.getValue();
        ProductPrice productPrice = productPriceList.get(productName);
        return productPrice.getOffer()
                .map(offer -> offer.calculatePrice(productCount, productPrice.getUnitPrice()))
                .orElseGet(() -> productPrice.getUnitPrice().multiply(new BigDecimal(productCount)));
    }
}
