package supermarket.services;

import org.springframework.stereotype.Service;
import supermarket.jpa.ProductRepository;

@Service
public class ProductDao {
    private ProductRepository productRepository;

    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
