package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.domain.PromotionRequest;
import supermarket.jpa.Product;
import supermarket.jpa.ProductRepository;
import supermarket.jpa.Promotion;
import supermarket.jpa.PromotionRepository;

@Service
public class PromotionDao {
    private PromotionRepository promotionRepository;
    private ProductRepository productRepository;

    public PromotionDao(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    public Promotion addPromotion(final PromotionRequest request){
        Promotion promotion = Promotion.builder()
                .quantity(request.getQuantity())
                .pricePerQuantity(request.getPricePerQuantity())
                .product(getProduct(request.getSku()))
                .build();
        //TODO figure out what happens if one already exists
        return promotionRepository.save(promotion);
    }

    private Product getProduct(final String sku) {
        return productRepository.findBySku(sku);
    }
}
