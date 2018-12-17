package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.domain.PromotionRequest;
import supermarket.jpa.Product;
import supermarket.jpa.Promotion;
import supermarket.jpa.PromotionRepository;

@Service
public class PromotionDao {
    private PromotionRepository promotionRepository;
    private ProductDao productDao;

    public PromotionDao(PromotionRepository promotionRepository, ProductDao productDao) {
        this.promotionRepository = promotionRepository;
        this.productDao = productDao;
    }

    public Promotion addPromotion(final PromotionRequest request) {
        Product product = productDao.getProduct(request.getSku());
        Promotion promotion = Promotion.builder()
                .quantity(request.getQuantity())
                .pricePerQuantity(request.getPricePerQuantity())
                .product(product)
                .build();
        deleteAnyExistingPromotionForProduct(product);
        return promotionRepository.save(promotion);
    }

    private void deleteAnyExistingPromotionForProduct(final Product product) {
        Promotion existingPromotion = promotionRepository.findByProduct(product);
        if (existingPromotion != null) {
            promotionRepository.delete(existingPromotion);
        }
    }
}
