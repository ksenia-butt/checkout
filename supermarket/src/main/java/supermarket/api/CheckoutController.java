package supermarket.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import supermarket.domain.TotalPriceResponse;
import supermarket.services.CheckoutService;

import java.util.List;

@RestController
public class CheckoutController {
    private CheckoutService checkoutService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutController.class);

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/totalPrice")
    public TotalPriceResponse getTotalPrice(@RequestParam List<String> items) {
        LOGGER.info("Processing calculateTotalPrice request for items={}", items);
        return checkoutService.calculateTotalPrice(items);
    }
}
