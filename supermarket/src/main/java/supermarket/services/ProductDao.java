package supermarket.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import supermarket.exception.ProductNotFoundException;
import supermarket.jpa.Product;
import supermarket.jpa.ProductRepository;

@Service
public class ProductDao {
    private ProductRepository productRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);

    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(final String sku) {
        LOGGER.info("Attempting to retrieve product by SKU={}", sku);
        Product product = productRepository.findBySku(sku);
        if (product != null) {
            return product;
        } else {
            LOGGER.error("Could not find product with sku={}", sku);
            throw new ProductNotFoundException();
        }
    }
}
