package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.domain.TotalPriceResponse;
import supermarket.jpa.Product;
import supermarket.jpa.Promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutService {
    private ProductDao productDao;

    public CheckoutService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public TotalPriceResponse calculateTotalPrice(final List<String> items) {
        int totalPrice = 0;
        Map<String, Integer> aggregatedProducts = aggregateItems(items);
        for (String productSku : aggregatedProducts.keySet()) {
            Product product = productDao.findProduct(productSku);
            int quantity = aggregatedProducts.get(productSku);
            int totalPriceForProduct = calculateProductTotalPrice(product, quantity);
            totalPrice = totalPrice + totalPriceForProduct;
        }
        return new TotalPriceResponse(totalPrice);
    }

    private int calculateProductTotalPrice(final Product product, final int quantityOfItems) {
        Promotion promotion = product.getPromotion();
        if(promotion != null && quantityOfItems >= promotion.getQuantity()){
            return applyPromotionToPrice(product, promotion, quantityOfItems);
        } else {
            return product.getPrice() * quantityOfItems;
        }
    }

    private int applyPromotionToPrice(final Product product, final Promotion promotion, final int quantity) {
        int promotionCount = quantity / promotion.getQuantity();
        int promotionTotalPrice = promotionCount * promotion.getPricePerQuantity();
        int nonPromotionItems = quantity % promotion.getQuantity();
        int nonPromotionItemsTotalPrice =nonPromotionItems * product.getPrice();
        return promotionTotalPrice + nonPromotionItemsTotalPrice;
    }

    private Map<String, Integer> aggregateItems(List<String> items) {
        Map<String, Integer> aggregatedMap = new HashMap<>();
        for (String item : items) {
            if (!aggregatedMap.containsKey(item)) {
                aggregatedMap.put(item, 1);
            } else {
                Integer previousNumber = aggregatedMap.get(item);
                aggregatedMap.replace(item, previousNumber + 1);
            }
        }
        return aggregatedMap;
    }
}
