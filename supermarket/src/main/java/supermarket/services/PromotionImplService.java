package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.domain.PromotionRequest;
import supermarket.jpa.Promotion;

@Service
public class PromotionImplService implements PromotionService{
    private PromotionDao promotionDao;

    public PromotionImplService(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    public Promotion addPromotion(final PromotionRequest promotionRequest) {
        return promotionDao.addPromotion(promotionRequest);
    }
}
