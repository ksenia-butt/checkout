package supermarket.services;

import supermarket.domain.TotalPriceResponse;

import java.util.List;

public interface CheckoutService {
    TotalPriceResponse calculateTotalPrice(final List<String> skus);
}
