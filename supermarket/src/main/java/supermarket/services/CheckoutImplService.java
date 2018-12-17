package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.domain.AppliedPromotion;
import supermarket.domain.PurchaseItemInfo;
import supermarket.domain.TotalPriceResponse;
import supermarket.enums.Currency;
import supermarket.jpa.Product;
import supermarket.jpa.Promotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutImplService implements CheckoutService {
    private ProductDao productDao;

    public CheckoutImplService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public TotalPriceResponse calculateTotalPrice(final List<String> skus) {
        TotalPriceResponse.TotalPriceResponseBuilder responseBuilder = TotalPriceResponse.builder();
        List<PurchaseItemInfo> purchaseItemInfoList = new ArrayList<>();
        List<AppliedPromotion> appliedPromotions = new ArrayList<>();
        Map<String, Integer> aggregatedProducts = aggregateItems(skus);
        int totalPrice = 0;
        for (String sku : aggregatedProducts.keySet()) {
            final Product product = productDao.getProduct(sku);
            final int quantity = aggregatedProducts.get(sku);
            final int totalPriceForProduct = calculateProductTotalPrice(product, quantity);
            if (product.getPromotion() != null) {
                appliedPromotions.add(createAppliedPromotion(product.getPromotion()));
            }
            purchaseItemInfoList.add(createPurchaseItem(product, quantity, totalPriceForProduct));
            totalPrice += totalPriceForProduct;
        }
        return responseBuilder
                .purchasedItems(purchaseItemInfoList)
                .appliedPromotions(appliedPromotions)
                .totalPrice(totalPrice)
                .currency(Currency.GBP)
                .build();
    }

    private AppliedPromotion createAppliedPromotion(final Promotion promotion) {
        String promotionInfo = promotion.getProduct().getProductName() + ": Get " + promotion.getQuantity() + " for "
                + promotion.getPricePerQuantity();
        return new AppliedPromotion(promotionInfo);
    }

    private PurchaseItemInfo createPurchaseItem(final Product product, final int quantity, final int totalPriceForProduct) {
        return PurchaseItemInfo.builder()
                .sku(product.getSku())
                .description(product.getProductName())
                .price(product.getPrice())
                .quantity(quantity)
                .totalPrice(totalPriceForProduct)
                .build();
    }

    private int calculateProductTotalPrice(final Product product, final int quantityOfItems) {
        Promotion promotion = product.getPromotion();
        if (promotion != null && quantityOfItems >= promotion.getQuantity()) {
            return applyPromotionToPrice(product, promotion, quantityOfItems);
        } else {
            return product.getPrice() * quantityOfItems;
        }
    }

    /*
     * Checks how many items of the quantity are qualified for promotion and applies discount to them.
     * Items outside of promotion are added at a normal price.
     * e.g. if an item is priced at £4 with an offer of 3 for £10
     * then for 4 items the price is worked out as 3 for £10 + 1 for £4 = £14
     */
    private int applyPromotionToPrice(final Product product, final Promotion promotion, final int quantity) {
        int promotionCount = quantity / promotion.getQuantity();
        int promotionTotalPrice = promotionCount * promotion.getPricePerQuantity();
        int nonPromotionItems = quantity % promotion.getQuantity();
        int nonPromotionItemsTotalPrice = nonPromotionItems * product.getPrice();
        return promotionTotalPrice + nonPromotionItemsTotalPrice;
    }

    /*
     * Takes a list of items which may contain duplicates
     * and converts it into a map in which a key is an item and value is that item's occurrence in the list
     */
    private Map<String, Integer> aggregateItems(final List<String> items) {
        Map<String, Integer> aggregatedMap = new HashMap<>();
        for (String item : items) {
            if (!aggregatedMap.containsKey(item)) {
                aggregatedMap.put(item, 1);
            } else {
                Integer previousQuantity = aggregatedMap.get(item);
                aggregatedMap.replace(item, ++previousQuantity);
            }
        }
        return aggregatedMap;
    }
}
