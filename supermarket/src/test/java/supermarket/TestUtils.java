package supermarket;

import supermarket.jpa.Product;
import supermarket.jpa.Promotion;

public class TestUtils {
    public static Product createProduct(final String sku) {
        return new Product(sku, "Oranges", 500, null);
    }

    public static Product createProduct(final String sku, final String name, final int price) {
        return new Product(sku, name, price, null);
    }

    public static Promotion createPromotion(final int quantity, final int price, Product product) {
        return new Promotion(1L, quantity, price, product);
    }
}
