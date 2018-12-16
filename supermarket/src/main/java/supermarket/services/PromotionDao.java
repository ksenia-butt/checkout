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
        Product product = getProduct(request.getSku());
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
        if(existingPromotion != null){
            promotionRepository.delete(existingPromotion);
        }
    }

    private Product getProduct(final String sku) {
        //todo add check for null
        return productRepository.findBySku(sku);
    }
}
