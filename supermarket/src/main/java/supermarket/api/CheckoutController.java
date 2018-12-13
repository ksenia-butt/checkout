package supermarket.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import supermarket.domain.TotalPriceResponse;
import supermarket.services.CheckoutService;

@RestController
public class CheckoutController {
private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/totalPrice")
    public TotalPriceResponse getTotalPrice(){
        return checkoutService.calculateTotalPrice();
    }
}
