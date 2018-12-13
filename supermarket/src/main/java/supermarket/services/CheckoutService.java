package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.domain.TotalPriceResponse;

@Service
public class CheckoutService {
    public TotalPriceResponse calculateTotalPrice(){
        return new TotalPriceResponse(1000);
    }
}
