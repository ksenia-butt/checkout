package supermarket.services;

import supermarket.domain.PromotionRequest;
import supermarket.jpa.Promotion;

public interface PromotionService {
    Promotion addPromotion(final PromotionRequest promotionRequest);
}
