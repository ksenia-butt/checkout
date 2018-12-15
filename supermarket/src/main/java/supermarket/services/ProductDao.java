package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.jpa.Product;
import supermarket.jpa.ProductRepository;

@Service
public class ProductDao {
    private ProductRepository productRepository;

    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProduct(final String sku){
        //TODO catch exception if not found
        return productRepository.findBySku(sku);
    }
}
