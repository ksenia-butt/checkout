package supermarket.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {

    @GetMapping("/totalPrice")
    public String getTotalPrice(){
        return "Test Successful";
    }
}
