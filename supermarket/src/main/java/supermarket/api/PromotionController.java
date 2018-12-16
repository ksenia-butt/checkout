package supermarket.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supermarket.domain.PromotionRequest;
import supermarket.jpa.Promotion;
import supermarket.services.PromotionService;

import javax.validation.Valid;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("/promotion")
public class PromotionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionController.class);
    private PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    //TODO add an endpoint to post multiple promotions at a time (check doesn't contain same product promos)
    @PostMapping
    public void addPromotion(@Valid @RequestBody PromotionRequest promotionRequest) {
        LOGGER.info(append("sku", promotionRequest.getSku())
                        .and(append("quantity", promotionRequest.getQuantity()))
                        .and(append("pricePerQuantity", promotionRequest.getPricePerQuantity())),
                "Received addPromotion request");
        Promotion promotion = promotionService.addPromotion(promotionRequest);
        LOGGER.info(append("id", promotion.getId())
                        .and(append("sku", promotion.getProduct().getSku()))
                        .and(append("quantity", promotion.getQuantity()))
                        .and(append("pricePerQuantity", promotion.getPricePerQuantity())),
                "Created promotion");
    }
}
